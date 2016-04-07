package com.java.myrotiuk.repository.order;

import java.util.Optional;

import com.java.myrotiuk.domain.Order;

public interface OrderRepository {
	long saveOrder(Order newOrder);
	Optional<Order> getOrder(long orderId);
	long updateOrder(Order order);
}
