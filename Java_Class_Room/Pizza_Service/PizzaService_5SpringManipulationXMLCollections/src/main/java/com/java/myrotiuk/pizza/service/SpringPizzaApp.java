package com.java.myrotiuk.pizza.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.repository.PizzaRepository;

public class SpringPizzaApp {
	public static void main(String[] args) {
//		ApplicationContext appContext = new ClassPathXmlApplicationContext("sources/appContext.xml");
//		System.out.println(appContext.getApplicationName());
		ConfigurableApplicationContext repositoryContext =
				new ClassPathXmlApplicationContext("repositoryContext.xml");
		ConfigurableApplicationContext appContext = 
				new ClassPathXmlApplicationContext( new String[]{"appContext.xml"},false);
		
		appContext.setParent(repositoryContext);
		appContext.refresh();
		
		System.out.println(appContext.getApplicationName());
		PizzaRepository pizzaRepository = (PizzaRepository)appContext.getBean("pizzaRepository");
		System.out.println(pizzaRepository.getPizzaByID(1));
		
		OrderService orderService = (OrderService) appContext.getBean("orderService");
		
        Order order = orderService.placeNewOrder(null, 1);//, 2, 3);
        System.out.println(order);

        
        Pizza pizza = appContext.getBean(Pizza.class);
        System.out.println("pizza=> "+pizza);
        
        Customer customer = appContext.getBean(Customer.class);
        System.out.println("customer = "+customer);
        
        ApplicationContext parent = appContext.getParent();
        System.out.println("parent "+parent);
        repositoryContext.close();
		appContext.close();
	} 
}
