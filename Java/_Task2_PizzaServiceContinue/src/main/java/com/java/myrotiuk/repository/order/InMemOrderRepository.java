package com.java.myrotiuk.repository.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.Order;

@Repository("orderRepository")
public class InMemOrderRepository {//implements OrderRepository {

	private List<Order> orders = new ArrayList<>();
	//@Override
	public long insert(Order newOrder) {
		orders.add(newOrder);
    	return newOrder.getId();
	}
	
	public void update(Order order){
		long id = 0;
		for(Order o :orders){
			if(o.getId() == order.getId()){
				o = order;
				id = order.getId();
			}
		}
		//return id;
	}
	
	public List<Order> getOrders(){
		return orders;
	}

	//@Override
	public Optional<Order> getOrder(long orderId) {
		for(Order order: orders){
			if(order.getId() == orderId){
				return Optional.of(order);
			}
		}
		return Optional.empty();
	}

	//@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Order find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
}
