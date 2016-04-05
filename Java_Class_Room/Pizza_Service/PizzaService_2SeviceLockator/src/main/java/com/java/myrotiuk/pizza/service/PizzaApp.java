package com.java.myrotiuk.pizza.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.infrastructure.ServiceLocator;

public class PizzaApp {
	public static void main(String[] args) {
       
		Customer customer = new Customer(1,"John");
		Order order;
		
		OrderService orderService = (OrderService) ServiceLocator.getInstance().lookup("orderService");
		
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
        Map<String, String> m = new HashMap<>();
        System.out.println(m.get("sd"));
	}
}
