package com.java.myrotiuk.service.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;
import com.java.myrotiuk.service.card.AccruedCardService;
import com.java.myrotiuk.service.discount.DiscountService;

public class SimpleOrderServiceTest {

	private SimpleOrderService orderService;
	private Customer customer;
	PizzaRepository pizzaRepository;
	OrderRepository orderRepository;
	AccruedCardService cardService;
	DiscountService discountService;
	
	@Before
	public void setUp() throws Exception {
		pizzaRepository = mock(PizzaRepository.class);
		orderRepository = mock(OrderRepository.class);
		cardService = mock(AccruedCardService.class);
		discountService = mock(DiscountService.class);
		customer = mock(Customer.class);
		orderService = new SimpleOrderService(pizzaRepository, orderRepository, cardService, discountService);
		
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionWhenPlaceNewOrderAsCountOfPizzasMorethen10() {
		orderService.placeNewOrder(customer, 1,4,2,7,8,9,7,3,17,5,6);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionWhenPlaceNewOrderAsThereIsNoPizzasItems() {
		orderService.placeNewOrder(customer);
	}
	
	@Test
	public void shouldReturnAnOrderWithStatusNew(){
		SimpleOrderService orderServiceSpy = spy(orderService);
		doReturn(1).when(orderServiceSpy).awardCard(customer);//.thenReturn(1);
		Order newOrder = new Order();
		when(orderServiceSpy.createOrder()).thenReturn(newOrder);
		Order order = orderServiceSpy.placeNewOrder(customer, 1,2,3);
		System.out.println(order.getOrderStatus());
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
	}
	
	@Test
	public void shouldReturnAnOrderWithThreePizzas(){
		SimpleOrderService orderServiceSpy = spy(orderService);
		doReturn(2).when(orderServiceSpy).awardCard(customer);
		Order newOrder = new Order();
		when(orderServiceSpy.createOrder()).thenReturn(newOrder);
		Order order = orderServiceSpy.placeNewOrder(customer, 1,2,3);
		List<Pizza> pizzas = order.getPizzas();
		assertEquals(3, pizzas.size());
	}

	@Test
	public void shouldReturnIdOfCardIfThetPresent(){
		AccruedCard card = mock(AccruedCard.class);
		Optional<AccruedCard> optCard = Optional.of(card);
		when(cardService.findCardByCustomer(customer)).thenReturn(optCard);
		when(card.getId()).thenReturn(1);
		int result = orderService.awardCard(customer);
		assertEquals(1, result);
	}
	
	@Test
	public void shouldReturnIdOfCardIfCardIsNew(){
		AccruedCard card = mock(AccruedCard.class);
		Optional<AccruedCard> optCard = Optional.empty();
		when(cardService.findCardByCustomer(customer)).thenReturn(optCard);
		when(cardService.giveCardToCustomer(customer)).thenReturn(1);
		int result = orderService.awardCard(customer);
		assertEquals(1, result);
	}
	
	
	
}
