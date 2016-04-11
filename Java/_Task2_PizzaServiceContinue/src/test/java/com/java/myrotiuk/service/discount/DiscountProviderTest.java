package com.java.myrotiuk.service.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.discount.Discount;
import com.java.myrotiuk.domain.discount.DiscountForMoreThen4Pizzas;
import com.java.myrotiuk.domain.discount.DiscountFromAccruedCard;
import com.java.myrotiuk.service.card.AccruedCardService;

public class DiscountProviderTest {

	private DiscountProvider discountProvider;
	private AccruedCardService cardService;
	
	@Before
	public void setUp() throws Exception {
		cardService = mock(AccruedCardService.class);
		discountProvider = new DiscountProvider(cardService);
	}     

	@Test
	public void shouldReturn2Discounts() {
		Order order = mock(Order.class);
		List<Discount> discounts =  discountProvider.provideDiscounts(order);
		assertEquals(2, discounts.size());
	}
	
	
	@Test
	public void shouldCorectlyConfigureDiscounts(){
		DiscountProvider testedInstance = spy(discountProvider);
		Order order = mock(Order.class);
		AccruedCard card = mock(AccruedCard.class);
		Customer customer = mock(Customer.class);
		double price = 100.0;
		List<Pizza> pizzas = mock(List.class);
		
		DiscountFromAccruedCard discountCard = mock(DiscountFromAccruedCard.class);
		DiscountForMoreThen4Pizzas discountPizza = mock(DiscountForMoreThen4Pizzas.class);
		
		when(order.getCustomer()).thenReturn(customer);
		when(order.getOrderPrice()).thenReturn(price);
		when(order.getPizzas()).thenReturn(pizzas);
		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.of(card));
		
		when(testedInstance.createDiscountForMoreThen4Pizzas(pizzas)).thenReturn(discountPizza);
		when(testedInstance.createDiscountFromAccruedCard(price, Optional.of(card))).thenReturn(discountCard);
		
		List<Discount> discounts = testedInstance.provideDiscounts(order);
		
		assertArrayEquals(new Discount[]{
				discountPizza,
				discountCard
		}, discounts.toArray());
		
	}

}
