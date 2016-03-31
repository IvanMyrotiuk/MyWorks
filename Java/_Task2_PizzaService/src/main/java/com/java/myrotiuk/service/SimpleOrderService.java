package com.java.myrotiuk.service;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.Status;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.order.InMemOrderRepository;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.InMemPizzaRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;

public class SimpleOrderService implements OrderService {

	private PizzaRepository pizzaRepository = new InMemPizzaRepository();
	private OrderRepository orderRepository = new InMemOrderRepository();

	@Override
/*	public Order placeNewOrder(Customer customer, Integer... pizzasID) {

		int countPizzas = pizzasID.length;
		
		List<Pizza> pizzas = pizzasByArrOfId(pizzasID);

		Order newOrder = createOrder(customer, pizzas);

		orderRepository.saveOrder(newOrder); // set Order Id and save Order to
												// in-memory list
		return newOrder;
	}
*/
	public Order placeNewOrder(Customer customer, Integer... pizzasID) {

		//int countPizzas = pizzasID.length;
		
		
//		if(!checkNumPizza(pizzasID.length)){
//			                                                                                                                                                                                                                   
//		}else{
			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);

			Order newOrder = createOrder(customer, pizzas);

			orderRepository.saveOrder(newOrder); // set Order Id and save Order to
													// in-memory list
			return newOrder;
//		}
		
	}
	
	private boolean checkNumPizza(int num){
		if(num <= 10){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean setOrderStatusToProgress(Order order){
		return order.setStatusToProgress(order);
	}
	
	public boolean setOrderStatusToCancel(Order order){
		return order.setStatusToCancel(order);
	}
	
	public boolean setOrderStatusToDone(Order order){
		return order.setStatusToDone(order);
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