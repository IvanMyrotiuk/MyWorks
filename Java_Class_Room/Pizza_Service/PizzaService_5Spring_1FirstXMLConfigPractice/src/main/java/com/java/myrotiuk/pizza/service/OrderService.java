package com.java.myrotiuk.pizza.service;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;

public interface OrderService {

	Order placeNewOrder(Customer customer, Integer... pizzasID);

}