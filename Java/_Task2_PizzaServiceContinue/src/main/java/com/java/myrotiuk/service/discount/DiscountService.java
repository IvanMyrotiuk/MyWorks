package com.java.myrotiuk.service.discount;

import javax.persistence.EntityManager;

import com.java.myrotiuk.domain.Order;

public interface DiscountService {
	
	double getDiscount(Order order);
	
}
