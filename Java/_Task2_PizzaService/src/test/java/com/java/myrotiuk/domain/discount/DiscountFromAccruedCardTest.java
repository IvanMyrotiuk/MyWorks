package com.java.myrotiuk.domain.discount;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.swing.text.html.Option;
public class DiscountFromAccruedCardTest {

	private DiscountFromAccruedCard discountCard;
	
	private Order order;
	private Customer customer;
	private AccruedCard card;
	private Optional<AccruedCard> optionalAccruedCard;
	
	@Before
	public void setUp() throws Exception {
		discountCard = new DiscountFromAccruedCard();
		order = mock(Order.class);
		customer = mock(Customer.class);
		
		card = mock(AccruedCard.class);
//		optionalAccruedCard = mock(Optional.class);
	}

	@Test
	public void shouldReturnThirtyPercentFromOrderPrice() {

		when(card.getAmount()).thenReturn(4000.0);
		
		//when(customer.getCard()).thenReturn(optionalAccruedCard);
		//doReturn(card).when(optionalAccruedCard).get();
		//when(order.getCustomer()).thenReturn(customer);
		
		when(order.getOrderPrice()).thenReturn(1000.0);
		
		Double result = discountCard.countDiscount(order);
		assertEquals(new Double(300.0), new Double(result));
	}

	@Test
	public void shouldReturnTenPercentFromAccruedCard() {

		when(card.getAmount()).thenReturn(1000.0);
		//when(customer.getCard()).thenReturn(optionalAccruedCard);
		//doReturn(card).when(optionalAccruedCard).get();
		
		//when(order.getCustomer()).thenReturn(customer);
		
		when(order.getOrderPrice()).thenReturn(1000.0);
		
		Double result = discountCard.countDiscount(order);
		assertEquals(new Double(100.0), new Double(result));
	}
	
}
