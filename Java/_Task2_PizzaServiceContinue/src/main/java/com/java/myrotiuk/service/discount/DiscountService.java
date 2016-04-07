package com.java.myrotiuk.service.discount;

import com.java.myrotiuk.domain.Order;

public interface DiscountService {
	
	double getDiscount(Order order);
	
}
