package com.java.myrotiuk.domain.discount;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

public class DiscountForMoreThen4PizzasTest {

	private Discount discountFor4Pizzas;
	
	@Before
	public void setUp() throws Exception {
		
		Set<Pizza> pizzas = new HashSet<>();
		
		pizzas.add(new Pizza(1,"Good day", 100, Type.SEA));
		pizzas.add(new Pizza(2,"Blue sky", 200, Type.MEAT));
		pizzas.add(new Pizza(3,"Woterfall", 255, Type.VEGETERIAN));
		pizzas.add(new Pizza(4,"Woterfall", 300, Type.VEGETERIAN));
		pizzas.add(new Pizza(5,"Woterfall", 250, Type.VEGETERIAN));
		
		discountFor4Pizzas = new DiscountForMoreThen4Pizzas(pizzas,5);
	}

	@Test
	public void shouldReturn30PercentFromHighestPriceIfNumOfPizzasMoreThen4() {
		double result = discountFor4Pizzas.countDiscount();
		assertEquals(90.0, result, 0);
	}
	
	@Test
	public void shouldReturnTrueWhenMoreThan4Pizzas(){
		assertTrue(discountFor4Pizzas.isApplicable());
	}
	
	@Test
	public void shouldReturnFalseWhenLessThan4Pizzas(){
		Set<Pizza> pizzas = new HashSet<>();
		
		pizzas.add(new Pizza("Good day", 100, Type.SEA));
		pizzas.add(new Pizza("Blue sky", 200, Type.MEAT));
		pizzas.add(new Pizza("Woterfall", 255, Type.VEGETERIAN));
		
		discountFor4Pizzas = new DiscountForMoreThen4Pizzas(pizzas, 3);
		assertFalse(discountFor4Pizzas.isApplicable());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionIfListOfPizzasIsNull(){
		new DiscountForMoreThen4Pizzas(null,7);
	}

}
