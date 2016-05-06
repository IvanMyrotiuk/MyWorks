package com.java.myrotiuk.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
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
import com.java.myrotiuk.repository.address.AddressRepository;
import com.java.myrotiuk.repository.customer.CustomerRepository;

import com.java.myrotiuk.repository.order.OrderRepository;

import com.java.myrotiuk.repository.pizza.PizzaRepository;
import com.java.myrotiuk.service.card.AccruedCardService;
import com.java.myrotiuk.service.card.SimpleAccruedCardService;
import com.java.myrotiuk.service.discount.DiscountProvider;
import com.java.myrotiuk.service.discount.DiscountService;
import com.java.myrotiuk.service.discount.SimpleDiscountService;


@Service// ("orderService")
public class SimpleOrderService implements OrderService {
	
	private AddressRepository addressRepository;
	private CustomerRepository customerRepository; 
	private PizzaRepository pizzaRepository; 
	private OrderRepository orderRepository; 
	private AccruedCardService cardService; 
	private DiscountService discountService; 

	@Autowired
	public SimpleOrderService(AddressRepository addressRepository, CustomerRepository customerRepository, PizzaRepository pizzaRepositoryy, 
			OrderRepository orderRepository, AccruedCardService cardService, DiscountService discountService) {
		this.addressRepository = addressRepository;
		this.customerRepository = customerRepository;
		this.pizzaRepository = pizzaRepositoryy;
		this.orderRepository = orderRepository;
		this.cardService = cardService;
		this.discountService = discountService;
	}

	@BenchMark
	public Order placeNewOrder(Address address, Integer... pizzasID) {

		int countPizzas = pizzasID.length;
		if (countPizzas > 0 && countPizzas <= 10) {

			//customerRepository.insert(address.getCustomer());
			addressRepository.insert(address);
			System.out.println("IDDDDDDDDDD"+ address.getCustomer().getId());
			awardCard(address.getCustomer(),"accrued card");

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

			orderRepository.insert(newOrder); // set Order Id and save Order
													// to in-memory list
			return newOrder;
		} else {
			throw new IllegalArgumentException("Inapropriate amount of pizzas");
		}
		
	}

	public long awardCard(Customer customer, String name) {
		Optional<AccruedCard> card = cardService.findCardByCustomer(customer);
		if (!card.isPresent()) {
			return cardService.giveCardToCustomer(customer, name);
		}
		return card.get().getId();
	}

	public Order processOrder(long orderId) {
		
		Optional<Order> order = orderRepository.getOrder(orderId);
		if (order.isPresent()) {
			Order processedOrder = order.get();
			if (processedOrder.getOrderStatus() == OrderStatus.NEW) {
				processedOrder.next();
				orderRepository.update(processedOrder);
				return processedOrder;
			} else {
				throw new StatusOrderException("You can not switch" + processedOrder.getOrderStatus() + " status to "
						+ OrderStatus.IN_PROGRESS);
			}
		}
		throw new WrongIdOfOrderException("There is no such order");
	}

	public Order completeOrder(long orderId) {
		
		Optional<Order> order = orderRepository.getOrder(orderId);
		if (order.isPresent()) {
			Order orderToComplete = order.get();
			if (orderToComplete.getOrderStatus() == OrderStatus.IN_PROGRESS) {
				Order completedOrder = orderToComplete.next();
				double priceAfterDiscount = completedOrder.getOrderPrice() - discountService.getDiscount(completedOrder);
				completedOrder.setOrderPrice(priceAfterDiscount);
				Address address = completedOrder.getAddress(); 
				Optional<AccruedCard> optionalAccruedCard = cardService
						.findCardByCustomer(address.getCustomer());
				if (optionalAccruedCard.isPresent()) {
					AccruedCard accruedCard = optionalAccruedCard.get();
					accruedCard.setAmount(accruedCard.getAmount() + priceAfterDiscount);
					cardService.updateCard(accruedCard);
				}
				orderRepository.update(completedOrder);
				return completedOrder;
			} else {
				throw new StatusOrderException(
						"You can not switch" + orderToComplete.getOrderStatus() + " status to " + OrderStatus.DONE);
			}
		} else {
			throw new WrongIdOfOrderException("There is no such order");
		}
	}

	public Order cancelOrder(long orderId) {
		Optional<Order> order = orderRepository.getOrder(orderId);
		if (order.isPresent()) {
			Order orderToCancel = order.get();
			Order canceledOrder = orderToCancel.cancel();
			orderRepository.update(canceledOrder);
			return canceledOrder;
		} else {
			throw new WrongIdOfOrderException("There is no such order");
		}
	}

	public boolean addPizzaToOrder(long orderId, Integer... pizzasID) {
		
		Optional<Order> currentOrder = orderRepository.getOrder(orderId);
		if (currentOrder.isPresent()) {
			Order order = currentOrder.get();
			int countOldPizzas = order.getPizzas().size();
			int countPizzas = pizzasID.length;
			int countFinalPizzas = countOldPizzas + countPizzas;
			if (countPizzas > 0 && countFinalPizzas <= 10 && order.getOrderStatus() == OrderStatus.NEW) {
				List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
				order.addPizzas(pizzas);
				orderRepository.update(order);
				return true;
			}
		}
		return false;
	}

	public boolean changeOrderDeletePizza(int orderId, Integer... pizzasID) {
		Optional<Order> currentOrder = orderRepository.getOrder(orderId);
		if (currentOrder.isPresent()) {
			Order order = currentOrder.get();
			return order.changeOrderDeletePizza(pizzasID);
		}
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
		List<Pizza> pizzas = new ArrayList<>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzaRepository.find(id)); // get Pizza from
															// predifined
															// in-memory list
		}
		return pizzas;
	}
}