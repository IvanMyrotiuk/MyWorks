package com.java.myrotiuk.domain.discount;

import com.java.myrotiuk.domain.Order;

public interface Discount {
	public double countDiscount(Order order);
}
