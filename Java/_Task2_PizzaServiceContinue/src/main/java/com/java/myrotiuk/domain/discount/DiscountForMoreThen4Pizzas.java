package com.java.myrotiuk.domain.discount;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.java.myrotiuk.domain.Pizza;

public class DiscountForMoreThen4Pizzas implements Discount {

	private final static double DISCOUNT = 0.3d;

	private List<Pizza> pizzas;
	
	public DiscountForMoreThen4Pizzas(List<Pizza> pizzas){
		if(pizzas == null){
			throw new IllegalArgumentException();
		}
		this.pizzas = pizzas;
	}
	
	public boolean isApplicable() {
		return pizzas.size() > 4;
	}

	@Override
	public double countDiscount() {
		Pizza pizzaMax = Collections.max(pizzas, new Comparator<Pizza>() {
			public int compare(Pizza z1, Pizza z2) {
				return (int) (z1.getPrice() - z2.getPrice());
			}
		});
		return pizzaMax.getPrice() * DISCOUNT;
	}
}
