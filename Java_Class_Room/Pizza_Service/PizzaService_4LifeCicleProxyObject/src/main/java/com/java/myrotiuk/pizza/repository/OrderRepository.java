package com.java.myrotiuk.pizza.repository;

import com.java.myrotiuk.pizza.domain.Order;

public interface OrderRepository {
	Long saveOrder(Order newOrder);
}
