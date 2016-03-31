package com.java.myrotiuk.service;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.order.InMemOrderRepository;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.InMemPizzaRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;

public class SimpleOrderService implements OrderService {
	
	/*private List<Pizza> pizzas = new ArrayList<>();
	{
		pizzas.add(new Pizza("Good day", 101.50, Type.SEA));
		pizzas.add(new Pizza("Blue sky", 111.50, Type.MEAT));
		pizzas.add(new Pizza("Woterfall", 131.50, Type.VEGETERIAN));
	}
	private List<Order> orders = new ArrayList<>();
    public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
            List<Pizza> pizzas = new ArrayList<>();

            for(Integer id : pizzasID){
                    pizzas.add(getPizzaByID(id));  // get Pizza from predifined in-memory list
            }
            Order newOrder = new Order(customer, pizzas);

            saveOrder(newOrder);  // set Order Id and save Order to in-memory list
            return newOrder;
    }
    
    private Pizza getPizzaByID(int id){
    	for(Pizza pizza: pizzas){
    		if(pizza.getId() == id)
    			return pizza;
    	}
    	return null;
    }
    
    private long saveOrder(Order newOrder){
    	orders.add(newOrder);
    	return newOrder.getId();
    }
    */
	//Refactoring
	private PizzaRepository pizzaRepository = new InMemPizzaRepository();
	private OrderRepository orderRepository = new InMemOrderRepository();
	
    @Override
	public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
    	
            List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
            
            Order newOrder = createOrder(customer, pizzas);

            orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
            return newOrder;
    }

	private Order createOrder(Customer customer, List<Pizza> pizzas) {
		Order newOrder = new Order(customer, pizzas);
		return newOrder;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<>();

		for(Integer id : pizzasID){
		        pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
		}
		return pizzas;
	}

}