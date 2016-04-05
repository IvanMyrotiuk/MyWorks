package com.java.myrotiuk.pizza.service;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.domain.Pizza;
//import com.java.myrotiuk.pizza.domain.Pizza.Type;
import com.java.myrotiuk.pizza.repository.OrderRepository;
import com.java.myrotiuk.pizza.repository.PizzaRepository;
import com.java.myrotiuk.pizza.repository.inmemorder.InMemOrderRepository;
import com.java.myrotiuk.pizza.repository.inmempizza.InMemPizzaRepository;

public class SimpleOrderService implements OrderService {
	/*
	private List<Pizza> pizzas = new ArrayList<Pizza>();
	{
		pizzas.add(new Pizza(1, "Tomato", 100.2, Type.VEGETERIAN));
        pizzas.add(new Pizza(2, "Meat", 99.0, Type.MEAT));
        pizzas.add(new Pizza(3, "Blue Sea", 100.2, Type.SEA));
	}
	
	private List<Order> orders = new ArrayList<Order>();
	
	 public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
         List<Pizza> pizzas = new ArrayList<Pizza>();
         
         for(Integer id : pizzasID){
                 pizzas.add(getPizzaByID(id));  // get Pizza from predifined in-memory list
         }
         Order newOrder = new Order(customer, pizzas);

         saveOrder(newOrder);  // set Order Id and save Order to in-memory list
         return newOrder;
	 }
	 
	 private Pizza getPizzaByID(int id){
		 int order = id - 1;
		 return pizzas.get(order);
	 }
	 
	 private void saveOrder(Order newOrder){
		 orders.add(newOrder);
	 }
	 */
	//REfactoring
//	private List<Pizza> pizzas = new ArrayList<Pizza>();
//	{
//		pizzas.add(new Pizza(1, "Tomato", 100.2, Type.VEGETERIAN));
//        pizzas.add(new Pizza(2, "Meat", 99.0, Type.MEAT));
//        pizzas.add(new Pizza(3, "Blue Sea", 100.2, Type.SEA));
//	}
	
	private OrderRepository orderRepository = new InMemOrderRepository();
	private PizzaRepository pizzaRepository = new InMemPizzaRepository();
	
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
	 
//	 private Pizza getPizzaByID(int id){
//		 int order = id - 1;
//		 return pizzas.get(order);
//	 }
	 
//	 private void saveOrder(Order newOrder){
//		 orders.add(newOrder);
//	 }
}
