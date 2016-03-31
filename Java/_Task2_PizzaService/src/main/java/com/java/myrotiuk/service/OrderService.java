package com.java.myrotiuk.service;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;

public interface OrderService {

	Order placeNewOrder(Customer customer, Integer... pizzasID);

}