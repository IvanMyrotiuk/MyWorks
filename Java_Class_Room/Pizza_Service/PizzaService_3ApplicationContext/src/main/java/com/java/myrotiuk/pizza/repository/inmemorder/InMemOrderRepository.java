package com.java.myrotiuk.pizza.repository.inmemorder;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.repository.OrderRepository;

public class InMemOrderRepository implements OrderRepository {
	private List<Order> orders = new ArrayList<Order>();
	public Long saveOrder(Order newOrder) {
		orders.add(newOrder);
		return newOrder.getId();
	}
}
