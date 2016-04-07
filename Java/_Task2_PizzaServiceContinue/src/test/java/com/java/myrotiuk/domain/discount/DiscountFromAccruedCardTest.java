package com.java.myrotiuk.domain.discount;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import static org.mockito.Mockito.*;

import java.io.OptionalDataException;
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
		
		customer = mock(Customer.class);
		card = mock(AccruedCard.class);
		optionalAccruedCard =Optional.of(card);
		
		
		
	}

	@Test
	public void shouldReturnThirtyPercentFromOrderPriceAsDiscountFromCardMoreThan30PercentOfCard() {

		discountCard = new DiscountFromAccruedCard(optionalAccruedCard, 1000);
		
		when(card.getAmount()).thenReturn(4000.0);
		
		Double result = discountCard.countDiscount();
		assertEquals(new Double(300.0), new Double(result));
	}

	@Test
	public void shouldReturnTenPercentFromAccruedCard() {

		discountCard = new DiscountFromAccruedCard(optionalAccruedCard, 1000);
		
		when(card.getAmount()).thenReturn(1000.0);
		
		Double result = discountCard.countDiscount();
		assertEquals(new Double(100.0), new Double(result));
	}
	
}
