/*package com.java.myrotiuk.service.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Address;
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
		Address address = mock(Address.class);
		double price = 100.0;
		Map<Pizza, Integer> pizzas = mock(Map.class);
		Set<Pizza> pizzasKeySet = mock(Set.class);//new HashSet<>();
		
		DiscountFromAccruedCard discountCard = mock(DiscountFromAccruedCard.class);
		DiscountForMoreThen4Pizzas discountPizza = mock(DiscountForMoreThen4Pizzas.class);
		
		when(order.getAddress()).thenReturn(address);
		when(address.getCustomer()).thenReturn(customer);
		when(order.getOrderPrice()).thenReturn(price);
		when(order.getPizzas()).thenReturn(pizzas);
		when(pizzas.keySet()).thenReturn(pizzasKeySet);
		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.of(card));
		
		List<Discount> discounts = testedInstance.provideDiscounts(order);
		
		assertArrayEquals(new Discount[]{
				discountPizza,
				discountCard
		}, discounts.toArray());
		
	}

}
*/