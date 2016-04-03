package com.java.myrotiuk.service.discount;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.discount.Discount;
import com.java.myrotiuk.domain.discount.DiscountForMoreThen4Pizzas;
import com.java.myrotiuk.domain.discount.DiscountFromAccruedCard;

public class SimpleDiscountService implements DiscountService {

	private List<Discount> discounts = new ArrayList<>();

	public SimpleDiscountService() {
		discounts.add(new DiscountForMoreThen4Pizzas());
		discounts.add(new DiscountFromAccruedCard());
	}

	@Override
	public double getPriceAfterDiscount(Order order) {
		double resultPriceAfterDiscount = order.getOrderPrice();
		for (Discount discount : discounts) {
			if (discount.isAplicable(order)) {
				resultPriceAfterDiscount -= discount.countDiscount(order);
				order.setOrderPrice(resultPriceAfterDiscount);
			}
		}
		return resultPriceAfterDiscount;
	}

}
