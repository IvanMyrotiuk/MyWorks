package com.java.myrotiuk.domain.discount;

import com.java.myrotiuk.domain.Order;

public class DiscountFromAccruedCard implements Discount {

	private static final double DISCOUNT_FROM_CARD = 0.1;
	private static final double DISCOUNT_FROM_ORDER_PRICE = 0.3;
	
	@Override
	public double countDiscount(Order order) {
		
		if(order.getCustomer().getCard() != null){
			double orderPriceDiscount = 
					order.getCountOrderPriceWithDiscount()* DISCOUNT_FROM_ORDER_PRICE;
			double cardDiscount = order.getCustomer().getCard().getAmount()*DISCOUNT_FROM_CARD;
			if(cardDiscount > orderPriceDiscount){
				return orderPriceDiscount;
			}
			return cardDiscount;
		}
		return 0;
	}

}
