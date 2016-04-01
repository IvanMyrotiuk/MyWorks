package com.java.myrotiuk.domain.discount;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

public class DiscountForMoreThen4PizzasTest {

	private Discount discountFor4Pizzas;
	
	@Before
	public void setUp() throws Exception {
		discountFor4Pizzas = new DiscountForMoreThen4Pizzas();
	}

	@Test
	public void shouldReturn30PercentFromHighestPriceIfNumOfPizzasMoreThen4() {
		Order order = Mockito.mock(Order.class);
		List<Pizza> pizzas = new ArrayList<>();
		
		pizzas.add(new Pizza("Good day", 100, Type.SEA));
		pizzas.add(new Pizza("Blue sky", 200, Type.MEAT));
		pizzas.add(new Pizza("Woterfall", 255, Type.VEGETERIAN));
		pizzas.add(new Pizza("Woterfall", 250, Type.VEGETERIAN));
		pizzas.add(new Pizza("Woterfall", 300, Type.VEGETERIAN));
		
		when(order.getPizzas()).thenReturn(pizzas);
		double result = discountFor4Pizzas.countDiscount(order);
		assertEquals(new Double(90.0), new Double(result));
	}

}
