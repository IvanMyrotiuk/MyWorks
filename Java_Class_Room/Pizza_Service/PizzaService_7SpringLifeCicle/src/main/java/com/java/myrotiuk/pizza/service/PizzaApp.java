package com.java.myrotiuk.pizza.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.infrastructure.ApplicationContext;
import com.java.myrotiuk.pizza.infrastructure.JavaConfigAApplicationContext;
import com.java.myrotiuk.pizza.infrastructure.ServiceLocator;
import com.java.myrotiuk.pizza.repository.PizzaRepository;

public class PizzaApp {
	public static void main(String[] args) throws Exception {
       
		Customer customer = new Customer(1,"John");

		Order order;

		ApplicationContext ac = new JavaConfigAApplicationContext();
		PizzaRepository pizzaRepository = (PizzaRepository) ac.getBean("pizzaRepository");
		System.out.println(pizzaRepository.getPizzaByID(1));
        
		OrderService orderService = (OrderService) ac.getBean("orderService");
		
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
	}
}
