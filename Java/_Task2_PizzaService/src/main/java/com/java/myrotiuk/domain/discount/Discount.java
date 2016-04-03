package com.java.myrotiuk.domain.discount;

import com.java.myrotiuk.domain.Order;

public interface Discount {
	double countDiscount(Order order);

	boolean isAplicable(Order order);
}
