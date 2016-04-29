package com.java.myrotiuk.service.discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.discount.Discount;
import com.java.myrotiuk.domain.discount.DiscountForMoreThen4Pizzas;
import com.java.myrotiuk.domain.discount.DiscountFromAccruedCard;
import com.java.myrotiuk.service.card.AccruedCardService;

@Service("discountProvider")
public class DiscountProvider {
	
	private AccruedCardService cardService;
	
	@Autowired
	public DiscountProvider(AccruedCardService cardService){
		this.cardService = cardService;
	}
	
	public List<Discount> provideDiscounts(Order order, EntityManager em){
		Optional<AccruedCard> accCard = cardService.findCardByCustomer(order.getAddress().getCustomer(), em);
		List<Discount> discounts = new ArrayList<>();
		discounts.add(createDiscountForMoreThen4Pizzas(order.getPizzas()));
		discounts.add(createDiscountFromAccruedCard(order.getOrderPrice(), accCard));
		return discounts;
	}

	DiscountFromAccruedCard createDiscountFromAccruedCard(double orderPrice, Optional<AccruedCard> accCard) {
		return new DiscountFromAccruedCard(accCard, orderPrice);
	}

	DiscountForMoreThen4Pizzas createDiscountForMoreThen4Pizzas(Map<Pizza, Integer> pizzas) {
		return new DiscountForMoreThen4Pizzas(pizzas.keySet());
	}
	
}
