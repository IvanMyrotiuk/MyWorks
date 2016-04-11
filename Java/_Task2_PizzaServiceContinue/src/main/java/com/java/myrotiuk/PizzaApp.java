package com.java.myrotiuk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.service.order.OrderService;
import com.java.myrotiuk.service.order.SimpleOrderService;

public class PizzaApp {

	public static void main(String[] args) {
		Customer customer = new Customer("John", new Address("Beverly Hills", "7777-777-77"));
		Order order;
		
		ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("appContext.xml");
		
		OrderService orderService = appContext.getBean(OrderService.class);//= new SimpleOrderService();
		order = orderService.placeNewOrder(customer, 1, 2, 3);
		System.out.println(order);
		
		appContext.close();
		
	}

}