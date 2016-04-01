package com.java.myrotiuk.service;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;

public interface OrderService {

	Order createOrder(Customer customer, Integer... pizzasID);
	Order placeNewOrder(Order newOrder);

}