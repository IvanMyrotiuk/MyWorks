package com.java.myrotiuk.domain.discount;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Order;

public class DiscountFromAccruedCard implements Discount {

	private static final double DISCOUNT_FROM_CARD = 0.1;
	private static final double DISCOUNT_FROM_ORDER_PRICE = 0.3;
	private Optional<AccruedCard> optionalAccruedCard;

	@Override
	public boolean isAplicable(Order order) {
		optionalAccruedCard = order.getCustomer().getCard();
		if (optionalAccruedCard.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double countDiscount(Order order) {

		AccruedCard accruedCard = optionalAccruedCard.get();
		double orderPriceDiscount = order.getOrderPrice() * DISCOUNT_FROM_ORDER_PRICE;
		double cardDiscount = accruedCard.getAmount() * DISCOUNT_FROM_CARD;
		optionalAccruedCard = Optional.empty();
		if (cardDiscount > orderPriceDiscount) {
			return orderPriceDiscount;
		}
		return cardDiscount;
	}

}
