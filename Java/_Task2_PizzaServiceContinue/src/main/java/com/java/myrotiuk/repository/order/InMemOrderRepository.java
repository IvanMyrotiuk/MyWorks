package com.java.myrotiuk.repository.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.java.myrotiuk.domain.Order;

public class InMemOrderRepository implements OrderRepository {

	private List<Order> orders = new ArrayList<>();
	@Override
	public long saveOrder(Order newOrder) {
		orders.add(newOrder);
    	return newOrder.getId();
	}
	
	public long updateOrder(Order order){
		long id = 0;
		for(Order o :orders){
			if(o.getId() == order.getId()){
				o = order;
				id = order.getId();
			}
		}
		return id;
	}
	
	public List<Order> getOrders(){
		return orders;
	}

	@Override
	public Optional<Order> getOrder(long orderId) {
		for(Order order: orders){
			if(order.getId() == orderId){
				return Optional.of(order);
			}
		}
		return Optional.empty();
	}
}
