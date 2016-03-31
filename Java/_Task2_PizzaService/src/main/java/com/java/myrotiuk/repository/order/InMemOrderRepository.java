package com.java.myrotiuk.repository.order;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Order;

public class InMemOrderRepository implements OrderRepository {

	private List<Order> orders = new ArrayList<>();
	@Override
	public long saveOrder(Order newOrder) {
		orders.add(newOrder);
    	return newOrder.getId();
	}
	
	public List<Order> getOrders(){
		return orders;
	}
}
