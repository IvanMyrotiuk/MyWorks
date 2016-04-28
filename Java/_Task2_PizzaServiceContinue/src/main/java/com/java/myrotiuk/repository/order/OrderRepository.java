package com.java.myrotiuk.repository.order;

import java.util.Optional;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.repository.BaseRepository;

public interface OrderRepository extends BaseRepository<Order>{
	Optional<Order> getOrder(long orderId);
}
