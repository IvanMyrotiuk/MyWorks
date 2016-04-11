package com.java.myrotiuk.service.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.discount.Discount;

public class SimpleDiscountServiceTest {

	private SimpleDiscountService simpleDiscountService;
	private DiscountProvider discountProvider;
	
	@Before
	public void setUp() throws Exception {
		discountProvider = mock(DiscountProvider.class);
		simpleDiscountService = new SimpleDiscountService(discountProvider);
	}

	@Test
	public void shouldReturn0WhenListIsEmpty() {
		
		Order order = mock(Order.class);
//		List discounts = mock(List.class);
//		Iterator iter = mock(Iterator.class);
//		when(discounts.iterator()).thenReturn(iter);
		
		List<Discount> discounts = new ArrayList<>();
		
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		
		double result = simpleDiscountService.getDiscount(order);
		
		assertEquals(0, result, 0);
	}
	
	@Test
	public void shouldReturnAmountEqualsAmountOfSingleDiscountIfDiscountApplicable(){
		
		double discountAmount = 30.0;
		
		Order order = mock(Order.class);
		List<Discount> discounts = new ArrayList<>();
		Discount discount = mock(Discount.class);
		discounts.add(discount);
		
		when(discount.isApplicable()).thenReturn(true);
		when(discount.countDiscount()).thenReturn(discountAmount);
		
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		
		double result = simpleDiscountService.getDiscount(order);
		
		assertEquals(discountAmount, result, 0);
	}
	
	
	@Test
	public void shouldReturn0IfDiscountIsNotApplicable(){
		
		Order order = mock(Order.class);
		List<Discount> discounts = new ArrayList<>();
		Discount discount = mock(Discount.class);
		discounts.add(discount);
		
		when(discount.isApplicable()).thenReturn(false);
		
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		
		double result = simpleDiscountService.getDiscount(order);
		
		assertEquals(0, result, 0);
	}
	
	@Test
	public void shouldReturnAmountEqualsSumOf2DiscountsIfDiscountsIsApplicabler(){
		Order order = mock(Order.class);
		
		List<Discount> discounts = new ArrayList<>();
		Discount discountFor4Pizzas = mock(Discount.class);
		Discount discountCard = mock(Discount.class);
		discounts.add(discountFor4Pizzas);
		discounts.add(discountCard);
		
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		
		when(discountFor4Pizzas.isApplicable()).thenReturn(true);
		when(discountCard.isApplicable()).thenReturn(true);
		when(discountFor4Pizzas.countDiscount()).thenReturn(30.0);
		when(discountCard.countDiscount()).thenReturn(70.0);

		double result = simpleDiscountService.getDiscount(order);//mock(Order.class)

		assertEquals(100.0, result, 0);
	}
	
	@Test
	public void shouldReturnAmountEqualsToOneApplicableDiscount(){
		
		Order order = mock(Order.class);
		
		List<Discount> discounts = new ArrayList<>();
		
		Discount discountFor4Pizzas = mock(Discount.class);
		Discount discountCard = mock(Discount.class);
		discounts.add(discountFor4Pizzas);
		discounts.add(discountCard);
		
		when(discountProvider.provideDiscounts(order)).thenReturn(discounts);
		
		when(discountFor4Pizzas.isApplicable()).thenReturn(false);
		when(discountCard.isApplicable()).thenReturn(true);
		when(discountFor4Pizzas.countDiscount()).thenReturn(30.0);
		when(discountCard.countDiscount()).thenReturn(70.0);
		
		double result = simpleDiscountService.getDiscount(order);
		assertEquals(70.0, result, 0);
		
	}
}
