package com.java.myrotiuk.service.discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.discount.Discount;
import com.java.myrotiuk.domain.discount.DiscountForMoreThen4Pizzas;
import com.java.myrotiuk.domain.discount.DiscountFromAccruedCard;
import com.java.myrotiuk.service.card.AccruedCardService;

public class DiscountProvider {
	
	private AccruedCardService cardService;
	
	public DiscountProvider(AccruedCardService cardService){
		this.cardService = cardService;
	}
	
	public List<Discount> provideDiscounts(Order order){
		Optional<AccruedCard> accCard = cardService.findCardByCustomer(order.getCustomer());
		List<Discount> discounts = new ArrayList<>();
		discounts.add(new DiscountForMoreThen4Pizzas(order.getPizzas()));
		discounts.add(new DiscountFromAccruedCard(accCard, order.getOrderPrice()));
		return discounts;
	}
}
