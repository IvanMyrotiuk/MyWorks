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
import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.exception.StatusOrderException;
import com.java.myrotiuk.exception.WrongIdOfOrderException;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.java.myrotiuk.repository.pizza.PizzaRepository;
import com.java.myrotiuk.service.card.AccruedCardService;
import com.java.myrotiuk.service.customer.CustomerService;
import com.java.myrotiuk.service.discount.DiscountService;

public class SimpleOrderServiceTest {

	private SimpleOrderService orderService;
	private Address address;
	private PizzaRepository pizzaRepository;
	private OrderRepository orderRepository;
	private AccruedCardService cardService;
	private DiscountService discountService;
	private CustomerService customerService;
	
	@Before
	public void setUp() throws Exception {
		pizzaRepository = mock(PizzaRepository.class);
		orderRepository = mock(OrderRepository.class);
		cardService = mock(AccruedCardService.class);
		discountService = mock(DiscountService.class);
		customerService = mock(CustomerService.class);
		address = mock(Address.class);
		orderService = new SimpleOrderService(pizzaRepository, orderRepository, cardService, discountService,customerService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionWhenPlaceNewOrderAsCountOfPizzasMorethen10() {
		orderService.placeNewOrder(address, 1,4,2,7,8,9,7,3,17,5,6);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionWhenPlaceNewOrderAsThereIsNoPizzasItems() {
		orderService.placeNewOrder(address);
	}
	
	@Test
	public void shouldReturnAnOrderWithStatusNew(){
		SimpleOrderService orderServiceSpy = spy(orderService);
		Order newOrder = new Order();
		when(orderServiceSpy.createOrder()).thenReturn(newOrder);
		Order order = orderServiceSpy.placeNewOrder(address, 1,2,3);
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
	}
	
	@Test
	public void shouldReturnAnOrderWithThreePizzas(){
		SimpleOrderService orderServiceSpy = spy(orderService);
		Order newOrder = new Order();
		when(orderServiceSpy.createOrder()).thenReturn(newOrder);
		Order order = orderServiceSpy.placeNewOrder(address, 1,2,3);
		//List<Pizza> pizzas = order.getPizzas();
		assertEquals(3, order.sizeOrder());//pizzas.size()
	}

//	@Test
//	public void shouldReturnIdOfCardIfThetPresent(){
//		AccruedCard card = mock(AccruedCard.class);
//		Optional<AccruedCard> optCard = Optional.of(card);
//		when(cardService.findCardByCustomer(customer)).thenReturn(optCard);
//		when(card.getId()).thenReturn(1);
//		int result = orderService.awardCard(customer);
//		assertEquals(1, result);
//	}
	
//	@Test
//	public void shouldReturnIdOfCardIfCardIsNew(){
//		AccruedCard card = mock(AccruedCard.class);
//		Optional<AccruedCard> optCard = Optional.empty();
//		when(cardService.findCardByCustomer(customer)).thenReturn(optCard);
//		when(cardService.giveCardToCustomer(customer)).thenReturn(1);
//		int result = orderService.awardCard(customer);
//		assertEquals(1, result);
//	}
	
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
	public void shoulCallSetPrice(){//dReturnOrderWithStatusDone(){
//		Order order = new Order();
//		Order orderSpy = spy(order);
//		orderSpy.setOrderStatus(OrderStatus.IN_PROGRESS);
//		when(orderRepository.getOrder(1)).thenReturn(Optional.of(orderSpy));
//		doReturn(20.0).when(orderSpy).getOrderPrice();
//		when(orderSpy.getCustomer()).thenReturn(customer);
//		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.empty());
//		Order orderProgress = orderService.completeOrder(1);
//		assertEquals(OrderStatus.DONE, orderProgress.getOrderStatus());
		Order order = mock(Order.class);
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		when(order.getOrderStatus()).thenReturn(OrderStatus.IN_PROGRESS);
		when(order.getOrderPrice()).thenReturn(100.0);
//		order.setOrderStatus(OrderStatus.DONE);
		when(order.next()).thenReturn(order);
		when(discountService.getDiscount(order)).thenReturn(30.0);
//		when(order.getCustomer()).thenReturn(customer);
//		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.empty());
		Order resultOrder = orderService.completeOrder(1);
//		System.out.println(resultOrder.getOrderStatus()); //how to check order status is done?
		verify(order).setOrderPrice(70.0);
		verify(orderRepository).update(order);
	}
	
	@Test
	public void shouldSetAmountToTheCard(){
		Order order = mock(Order.class);
		AccruedCard card = mock(AccruedCard.class);
		Customer customer = mock(Customer.class);
		when(orderRepository.getOrder(1)).thenReturn(Optional.of(order));
		when(order.getOrderStatus()).thenReturn(OrderStatus.DONE);
		when(order.getOrderPrice()).thenReturn(100.0);
		when(order.next()).thenReturn(order);
		when(discountService.getDiscount(order)).thenReturn(30.0);
		when(order.getAddress()).thenReturn(address);
		when(address.getCustomer()).thenReturn(customer);
		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.of(card));
		when(card.getAmount()).thenReturn(100.0);
		
		double resultOrder = orderService.pay(1);
		verify(card).setAmount(200.0);
		verify(cardService).updateCard(card);
	}
//	
//	@Test//!!!
//	public void shouldReturnAmountOfCard(){
////		Order order = new Order();
////		Order orderSpy = spy(order);
////		orderSpy.setOrderPrice(1000);
////		orderSpy.setOrderStatus(OrderStatus.IN_PROGRESS);
////		when(orderRepository.getOrder(1)).thenReturn(Optional.of(orderSpy));
////		when(discountService.getDiscount(orderSpy)).thenReturn(200.0);
////		when(orderSpy.getCustomer()).thenReturn(customer);
////		AccruedCard card = new AccruedCard(customer);
////		card.setAmount(300);
////		when(cardService.findCardByCustomer(customer)).thenReturn(Optional.of(card));
////		Order orderProgress = orderService.completeOrder(1);
////		assertEquals(1100.0, card.getAmount(), 0);
//		Order order = mock(Order.class);
//		
//	}
	
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
