package com.java.myrotiuk.domain.discount;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;

public class DiscountFromAccruedCard implements Discount {

	private static final double DISCOUNT_FROM_CARD = 0.1;
	private static final double DISCOUNT_FROM_ORDER_PRICE = 0.3;
	private Optional<AccruedCard> accruedCard;
	private double orderPrice;
	
	
	public DiscountFromAccruedCard(Optional<AccruedCard> accruedCard, double orderPrice) {
		this.accruedCard = accruedCard;
		this.orderPrice = orderPrice;
	}

	@Override
	public boolean isApplicable() {
		return accruedCard.isPresent();
	}

	@Override
	public double countDiscount() {

		AccruedCard accCard = accruedCard.get();
		double orderPriceDiscount =orderPrice * DISCOUNT_FROM_ORDER_PRICE;
		double cardDiscount = accCard.getAmount() * DISCOUNT_FROM_CARD;
		if (cardDiscount > orderPriceDiscount) {
			return orderPriceDiscount;
		}
		return cardDiscount;
	}

}
