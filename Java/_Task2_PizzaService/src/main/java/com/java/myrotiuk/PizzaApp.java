package com.java.myrotiuk;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.service.OrderService;
import com.java.myrotiuk.service.SimpleOrderService;

public class PizzaApp {
	 
    public static void main(String[] args) {
    	 Customer customer = new Customer("John",new Address("Beverly Hills","7777-777-77"));
            Order order;

            OrderService orderService = new SimpleOrderService();
            order = orderService.createOrder(customer, 1, 2, 3);
            order = orderService.placeNewOrder(order);
           // order = orderService.placeNewOrder(customer, 1, 2, 3);

           System.out.println(order);
    }

}