package com.java.myrotiuk.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.exception.WrongIdOfOrderException;
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

public class SimpleOrderService implements OrderService {

	private PizzaRepository pizzaRepository = new InMemPizzaRepository();
	private OrderRepository orderRepository = new InMemOrderRepository();
	private AccruedCardService cardService = new SimpleAccruedCardService(new InMemAccruedCardRepository());
	private DiscountService discountService = new SimpleDiscountService(new DiscountProvider(cardService));
	

//	public SimpleOrderService(PizzaRepository pizzaRepository, OrderRepository orderRepository,
//			AccruedCardService cardService) {
//		this.pizzaRepository = pizzaRepository;
//		this.orderRepository = orderRepository;
//		this.cardService = cardService;
//	}

	public Order placeNewOrder(Customer customer, Integer... pizzasID) {

		int countPizzas = pizzasID.length;
		if (countPizzas > 0 && countPizzas <= 10) {
			
			awardCard(customer);
			
			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
			Order newOrder = createOrder(customer, pizzas);

			orderRepository.saveOrder(newOrder); // set Order Id and save Order
													// to in-memory list
			return newOrder;
		} else {
			throw new IllegalArgumentException("Inapropriate amount of pizzas");
		}
	}
	
	public int awardCard(Customer customer){
		Optional<AccruedCard> card = cardService.findCardByCustomer(customer);
		if(!card.isPresent()){
			return cardService.giveCardToCustomer(customer);
		}
		return card.get().getId();
	}

	public Order processOrder(long orderId){
		Optional<Order> order = orderRepository.getOrder(orderId);
		if(order.isPresent()){
			Order processedOrder = order.get().next();
			orderRepository.updateOrder(processedOrder);
			return processedOrder;
		}
		throw new WrongIdOfOrderException("There is no such order");
	}
	
	public Order completeOrder(long orderId){
		Optional<Order> order = orderRepository.getOrder(orderId);
		if(order.isPresent()){
			Order orderToComplete = order.get();
			Order completedOrder = orderToComplete.next();
			//DiscountService discountService = new SimpleDiscountService();
			double priceAfterDiscount =completedOrder.getOrderPrice() - discountService.getDiscount(completedOrder);
			Optional<AccruedCard> optionalAccruedCard = cardService.findCardByCustomer(completedOrder.getCustomer());
			if(optionalAccruedCard.isPresent()){
				AccruedCard accruedCard = optionalAccruedCard.get();
				accruedCard.setAmount(accruedCard.getAmount()+priceAfterDiscount);
				cardService.updateCard(accruedCard);
			}
			orderRepository.updateOrder(completedOrder);
			return completedOrder;
		}else{
			throw new WrongIdOfOrderException("There is no such order");
		}
	}
	
	public Order cancelOrder(long orderId){
		Optional<Order> order = orderRepository.getOrder(orderId);
		if(order.isPresent()){
			Order orderToCancel = order.get();
			Order canceledOrder = orderToCancel.cancel();
			orderRepository.updateOrder(canceledOrder);
			return canceledOrder;
		}else{
			throw new WrongIdOfOrderException("There is no such order");
		}
	}
	
	public boolean addPizzaToOrder(int orderId, Integer... pizzasID) {

		Optional<Order> currentOrder = orderRepository.getOrder(orderId);
		if (currentOrder.isPresent()) {
			Order order = currentOrder.get();
			int countOldPizzas = order.getPizzas().size();
			int countPizzas = pizzasID.length;
			int countFinalPizzas = countOldPizzas + countPizzas;
			if (countPizzas > 0 && countFinalPizzas <= 10 && order.getOrderStatus() == OrderStatus.NEW) {
				List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
				order.addPizzas(pizzas);
				orderRepository.saveOrder(order);
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

	private Order createOrder(Customer customer, List<Pizza> pizzas) {

		Order newOrder = new Order(customer, pizzas);
		return newOrder;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzaRepository.getPizzaByID(id)); // get Pizza from
															// predifined
															// in-memory list
		}
		return pizzas;
	}
}