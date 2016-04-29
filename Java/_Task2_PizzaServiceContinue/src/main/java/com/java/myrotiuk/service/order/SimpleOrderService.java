package com.java.myrotiuk.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.exception.StatusOrderException;
import com.java.myrotiuk.exception.WrongIdOfOrderException;
import com.java.myrotiuk.infrustructure.BenchMark;
import com.java.myrotiuk.repository.card.InMemAccruedCardRepository;
import com.java.myrotiuk.repository.order.InMemOrderRepository;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.InMemPizzaRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;
import com.java.myrotiuk.service.card.AccruedCardService;
import com.java.myrotiuk.service.card.SimpleAccruedCardService;
import com.java.myrotiuk.service.discount.DiscountProvider;
import com.java.myrotiuk.service.discount.DiscountService;
import com.java.myrotiuk.service.discount.SimpleDiscountService;
import com.java.myrotiuk.service.entitymanager.EntityManagerService;

@Service// ("orderService")
public class SimpleOrderService implements OrderService {

	@Autowired
	private EntityManagerService ems;
	
	private PizzaRepository pizzaRepository; 
	private OrderRepository orderRepository; 
	private AccruedCardService cardService; 
	private DiscountService discountService; 

	@Autowired
	public SimpleOrderService(PizzaRepository pizzaRepositoryy, OrderRepository orderRepository,
			AccruedCardService cardService, DiscountService discountService) {
		this.pizzaRepository = pizzaRepositoryy;
		this.orderRepository = orderRepository;
		this.cardService = cardService;
		this.discountService = discountService;
	}

	@BenchMark
	public Order placeNewOrder(Address address, Integer... pizzasID) {

		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		int countPizzas = pizzasID.length;
		if (countPizzas > 0 && countPizzas <= 10) {

			em.persist(address.getCustomer());
			awardCard(address.getCustomer(),"accrued card", em);

			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
			Map<Pizza, Integer> pizzasQuantity = new HashMap<>();
			for(Pizza p : pizzas ){
				if(pizzasQuantity.containsKey(p)){
					Integer oldQ = pizzasQuantity.get(p);
					pizzasQuantity.put(p, oldQ + 1);
				}else{
					pizzasQuantity.put(p, 1);
				}
			}
			Order newOrder = createOrder();
			newOrder.setAddress(address);
			newOrder.setPizzas(pizzasQuantity);

			orderRepository.insert(newOrder, em); // set Order Id and save Order
													// to in-memory list
			em.getTransaction().commit();
			em.close();
			return newOrder;
		} else {
			em.close();
			throw new IllegalArgumentException("Inapropriate amount of pizzas");
		}
		
	}

	public long awardCard(Customer customer, String name, EntityManager em) {
//		EntityManager em = ems.createEntityManager();
//		em.getTransaction().begin();
		Optional<AccruedCard> card = cardService.findCardByCustomer(customer, em);
		if (!card.isPresent()) {
			return cardService.giveCardToCustomer(customer, name, em);
		}
//		em.getTransaction().commit();
//		em.close();
		return card.get().getId();
	}

	public Order processOrder(long orderId) {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		
		Optional<Order> order = orderRepository.getOrder(orderId, em);
		if (order.isPresent()) {
			Order processedOrder = order.get();
			if (processedOrder.getOrderStatus() == OrderStatus.NEW) {
				processedOrder.next();
				orderRepository.update(processedOrder, em);
				em.getTransaction().commit();
				em.close();
				return processedOrder;
			} else {
				em.close();
				throw new StatusOrderException("You can not switch" + processedOrder.getOrderStatus() + " status to "
						+ OrderStatus.IN_PROGRESS);
			}
		}
		em.close();
		throw new WrongIdOfOrderException("There is no such order");
	}

	public Order completeOrder(long orderId) {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		
		Optional<Order> order = orderRepository.getOrder(orderId, em);
		if (order.isPresent()) {
			Order orderToComplete = order.get();
			if (orderToComplete.getOrderStatus() == OrderStatus.IN_PROGRESS) {
				Order completedOrder = orderToComplete.next();
				double priceAfterDiscount = completedOrder.getOrderPrice()- discountService.getDiscount(completedOrder,em);
				completedOrder.setOrderPrice(priceAfterDiscount);
				Address address = completedOrder.getAddress(); 
				Optional<AccruedCard> optionalAccruedCard = cardService
						.findCardByCustomer(address.getCustomer(), em);
				if (optionalAccruedCard.isPresent()) {
					AccruedCard accruedCard = optionalAccruedCard.get();
					accruedCard.setAmount(accruedCard.getAmount() + priceAfterDiscount);
					cardService.updateCard(accruedCard, em);
				}
				orderRepository.update(completedOrder, em);
				em.getTransaction().commit();
				em.close();
				return completedOrder;
			} else {
				em.close();
				throw new StatusOrderException(
						"You can not switch" + orderToComplete.getOrderStatus() + " status to " + OrderStatus.DONE);
			}
		} else {
			em.close();
			throw new WrongIdOfOrderException("There is no such order");
		}
	}

	public Order cancelOrder(long orderId) {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		Optional<Order> order = orderRepository.getOrder(orderId, em);
		if (order.isPresent()) {
			Order orderToCancel = order.get();
			Order canceledOrder = orderToCancel.cancel();
			orderRepository.update(canceledOrder, em);
			em.getTransaction().commit();
			em.close();
			return canceledOrder;
		} else {
			em.close();
			throw new WrongIdOfOrderException("There is no such order");
		}
	}

	public boolean addPizzaToOrder(long orderId, Integer... pizzasID) {

		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		
		Optional<Order> currentOrder = orderRepository.getOrder(orderId, em);
		if (currentOrder.isPresent()) {
			Order order = currentOrder.get();
			int countOldPizzas = order.getPizzas().size();
			int countPizzas = pizzasID.length;
			int countFinalPizzas = countOldPizzas + countPizzas;
			if (countPizzas > 0 && countFinalPizzas <= 10 && order.getOrderStatus() == OrderStatus.NEW) {
				List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
				order.addPizzas(pizzas);
				orderRepository.insert(order, em);
				em.getTransaction().commit();
				em.close();
				return true;
			}
		}
		em.getTransaction().commit();
		em.close();
		return false;
	}

	public boolean changeOrderDeletePizza(int orderId, Integer... pizzasID) {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		Optional<Order> currentOrder = orderRepository.getOrder(orderId, em);
		if (currentOrder.isPresent()) {
			Order order = currentOrder.get();
			
			em.getTransaction().commit();
			em.close();
			return order.changeOrderDeletePizza(pizzasID);
		}
		em.getTransaction().commit();
		em.close();
		return false;
	}

	// private Order createOrder(Customer customer, List<Pizza> pizzas) {
	//
	// Order newOrder = new Order(customer, pizzas);
	// return newOrder;
	// }

	@Lookup
	protected Order createOrder() {
		return null;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		List<Pizza> pizzas = new ArrayList<>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzaRepository.find(id, em)); // get Pizza from
															// predifined
															// in-memory list
		}
		em.getTransaction().commit();
		em.close();
		return pizzas;
	}
}