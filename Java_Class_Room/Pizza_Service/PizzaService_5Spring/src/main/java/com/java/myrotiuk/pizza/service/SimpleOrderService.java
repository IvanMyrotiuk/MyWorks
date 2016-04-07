package com.java.myrotiuk.pizza.service;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.infrastructure.ServiceLocator;
//import com.java.myrotiuk.pizza.domain.Pizza.Type;
import com.java.myrotiuk.pizza.repository.OrderRepository;
import com.java.myrotiuk.pizza.repository.PizzaRepository;
import com.java.myrotiuk.pizza.repository.inmemorder.InMemOrderRepository;
import com.java.myrotiuk.pizza.repository.inmempizza.InMemPizzaRepository;

public class SimpleOrderService implements OrderService {
	
	private ServiceLocator locator = ServiceLocator.getInstance();
	
	private OrderRepository orderRepository; //= 
	//		(OrderRepository) locator.lookup("orderRepository");//new InMemOrderRepository();
	private PizzaRepository pizzaRepository; //= 
	//		(PizzaRepository) locator.lookup("pizzaRepository");//new InMemPizzaRepository();
	
	 public SimpleOrderService(OrderRepository orderRepository, PizzaRepository pizzaRepository) {
		this.orderRepository = orderRepository;
		this.pizzaRepository = pizzaRepository;
	}


	/* (non-Javadoc)
	 * @see com.java.myrotiuk.pizzaservice.OrderService#placeNewOrder(com.java.myrotiuk.pizzaservice.Customer, java.lang.Integer)
	 */
	public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
         List<Pizza> pizzas = pizzaByArrOfId(pizzasID);
         Order newOrder = createOrder(customer, pizzas);

         orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
         return newOrder;
	 }

	private Order createOrder(Customer customer, List<Pizza> pizzas) {
		Order newOrder = new Order(customer, pizzas);
		return newOrder;
	}

	private List<Pizza> pizzaByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();
         
         for(Integer id : pizzasID){
                 pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
         }
		return pizzas;
	}
}
