package com.java.myrotiuk.service.discount;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.domain.discount.Discount;
import com.java.myrotiuk.domain.discount.DiscountForMoreThen4Pizzas;
import com.java.myrotiuk.domain.discount.DiscountFromAccruedCard;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleDiscountServiceTest {

	
	private SimpleDiscountService simpleDiscountService;
	private DiscountProvider discountProvider;
	private Order order;
	private List<Discount> discounts;
	private Discount discount;
	
	
	@Before
	public void setUp() throws Exception {
		
		discountProvider = mock(DiscountProvider.class);
		simpleDiscountService = new SimpleDiscountService(discountProvider);
		order = mock(Order.class);
		discounts = mock(List.class);
		discount = mock(Discount.class);
	}

	@Test
	public void test() {
		Order order = mock(Order.class);
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		when(discount.countDiscount()).thenReturn((double) 20).thenReturn((double) 40);
		Double result = simpleDiscountService.getDiscount(order);
		assertTrue(new Double(60) == result);
	}

}
