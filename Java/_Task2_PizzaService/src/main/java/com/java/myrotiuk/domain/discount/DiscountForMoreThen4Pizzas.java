package com.java.myrotiuk.domain.discount;

import java.util.Collections;
import java.util.Comparator;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;

public class DiscountForMoreThen4Pizzas implements Discount {

	private final static double DISCOUNT = 0.3d;

	public boolean isAplicable(Order order) {
		return order.getPizzas().size() > 4;
	}

	@Override
	public double countDiscount(Order order) {
		Pizza pizzaMax = Collections.max(order.getPizzas(), new Comparator<Pizza>() {
			public int compare(Pizza z1, Pizza z2) {
				return (int) (z1.getPrice() - z2.getPrice());
			}
		});
		return pizzaMax.getPrice() * DISCOUNT;
	}
}
