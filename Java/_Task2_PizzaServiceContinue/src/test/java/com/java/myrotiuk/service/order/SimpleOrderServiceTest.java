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
import com.java.myrotiuk.exception.StatusOrderException;
import com.java.myrotiuk.exception.WrongIdOfOrderException;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;
import com.java.myrotiuk.service.card.AccruedCardService;
import com.java.myrotiuk.service.discount.DiscountService;

public class SimpleOrderServiceTest {

	private SimpleOrderService orderService;
	private Customer customer;
	private PizzaRepository pizzaRepository;
	private OrderRepository orderRepository;
	private AccruedCardService cardService;
	private DiscountService discountService;
	
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
	
	@Test(expected = WrongIdOfOrderException.class)
	public void shouldThrowAnExceptionAsThereIsNoSuchIdOfOrderToProcess(){
		when(orderRepository.getOrder(1)).thenReturn(Optional.empty());
		orderService.processOrder(1);
	}
	
	@Test
	public void shouldReturnAnOrderWithStatusInProgress(){
		Order order = new Order();
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		Order orderProgress = orderService.processOrder(1);
		assertEquals(OrderStatus.IN_PROGRESS, orderProgress.getOrderStatus());
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldThrowAnExceptionAsOrderNotInNewStatusToSwitchToStatusInProgress(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		Order orderProgress = orderService.processOrder(1);
	}
	
	@Test(expected = WrongIdOfOrderException.class)
	public void shouldThrowAnExceptionAsThereIsNoSuchIdOfOrderToComplete(){
		when(orderRepository.getOrder(1)).thenReturn(Optional.empty());
		orderService.completeOrder(1);
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldThrowAnExceptionAsOrderInAnotherStatusThenInProgressAndCannotbeSetInCompleteStatus(){
		Order order = new Order();
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		Order orderProgress = orderService.completeOrder(1);
	}
	
	@Test
	public void shouldReturnOrderWithStatusDone(){
		Order order = new Order();
		Order orderSpy = spy(order);
		orderSpy.setOrderStatus(OrderStatus.IN_PROGRESS);
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(orderSpy));
		doReturn(20.0).when(orderSpy).getOrderPrice();
		when(orderSpy.getCustomer()).thenReturn(customer);
		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.empty());
		Order orderProgress = orderService.completeOrder(1);
		assertEquals(OrderStatus.DONE, orderProgress.getOrderStatus());
	}
	
	@Test//!!!
	public void shouldReturnAmountOfCard(){
		Order order = new Order();
		Order orderSpy = spy(order);
		orderSpy.setOrderPrice(1000);
		orderSpy.setOrderStatus(OrderStatus.IN_PROGRESS);
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(orderSpy));
		when(discountService.getDiscount(orderSpy)).thenReturn(200.0);
		when(orderSpy.getCustomer()).thenReturn(customer);
		AccruedCard card = new AccruedCard(customer);
		card.setAmount(300);
		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.of(card));
		Order orderProgress = orderService.completeOrder(1);
		assertEquals(1100.0, card.getAmount(), 0);
	}
	
	@Test(expected = WrongIdOfOrderException.class)
	public void shouldThrouwAnExcrptionIfThereIsNOSuchOrderToCancel(){
		when(orderRepository.getOrder(1)).thenReturn(Optional.empty());
		orderService.completeOrder(1);
	}
	
	@Test
	public void shouldReturntOrderWithStatusCanceled(){
		Order order = new Order();
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		Order orderCanceled = orderService.cancelOrder(1);
		assertEquals(OrderStatus.CANCELED, orderCanceled.getOrderStatus());
	}
}
