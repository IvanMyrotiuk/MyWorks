package com.java.myrotiuk.pizza.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;

public class PizzaApp {
	public static void main(String[] args) {
       
//		Customer customer = new Customer(1,"John");
//		Customer customer2 = new Customer(2,"Lilu");
        
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1,"John"));
		customers.add(new Customer(2,"Lilu"));
		List<Order> orders = new ArrayList<>();
		//Order order;

		OrderService orderService = new SimpleOrderService();
		for(Customer customer:customers){
			orders.add(orderService.placeNewOrder(customer, 1, 2, 3));
		}
        
        //order = orderService.placeNewOrder(customer, 1, 2, 3);

        //System.out.println(Arrays.asList(orders));
        
        for(Order order: orders){
        	System.out.println(order);
        }
	}
}
