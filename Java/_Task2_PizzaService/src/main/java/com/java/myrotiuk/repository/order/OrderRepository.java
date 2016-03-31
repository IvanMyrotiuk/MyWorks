package com.java.myrotiuk.repository.order;

import com.java.myrotiuk.domain.Order;

public interface OrderRepository {
	long saveOrder(Order newOrder);
}
