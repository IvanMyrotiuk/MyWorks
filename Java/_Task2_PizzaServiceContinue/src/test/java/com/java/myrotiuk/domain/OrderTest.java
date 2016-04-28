package com.java.myrotiuk.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.exception.StatusOrderException;

public class OrderTest {

	private Order order;
	private Customer customer;
	
	@Before
	public void setUp() throws Exception {
		List<Pizza> pizzas = new ArrayList<>();
		
		pizzas.add(new Pizza(1,"Good day", 100, Type.SEA));
		pizzas.add(new Pizza(2,"Blue sky", 200, Type.MEAT));
		pizzas.add(new Pizza(3,"Woterfall", 255, Type.VEGETERIAN));
		pizzas.add(new Pizza(4,"Woterfall2", 300, Type.VEGETERIAN));
		pizzas.add(new Pizza(5,"Woterfall3", 250, Type.VEGETERIAN));
		customer = mock(Customer.class);
		this.order = new Order(customer, pizzas);
	}

	@Test
	public void shoulrReturnOrderWithInProgressStatus() {
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		Order orderToCheck = order.next();
		OrderStatus orderStatus = orderToCheck.getOrderStatus();
		assertEquals(OrderStatus.IN_PROGRESS, orderStatus); 
	}
	
	@Test
	public void shoulrReturnOrderWithCanceledStatus() {
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		Order orderToCheck = order.cancel();
		OrderStatus orderStatus = orderToCheck.getOrderStatus();
		assertEquals(OrderStatus.CANCELED, orderStatus); 
	}
	
	@Test
	public void shouldReturnTrueIfOrderStatusDone(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		order.next();
		assertEquals(OrderStatus.DONE, order.getOrderStatus());
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldTrowAnExceptionIfOrderStatusInProgressAndWeWantToSwitchToCancel(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		order.cancel();
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldTrowAnExceptionIfOrderStatusInCancelAndWeWantToSwitchToSomethingElse(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.CANCELED);
		order.next();
	}
	
	@Test
	public void shouldReturnTrueIfOrderStatusCancel(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.CANCELED);
		order.cancel();
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldTrowAnExceptionIfOrderStatusInDoneAndWeWantToSwitchToSomethingElse(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.DONE);
		order.next();
	}
	
	@Test(expected = StatusOrderException.class)
	public void shouldTrowAnExceptionIfOrderStatusInDoneAndWeWantToSwitchToCanceled(){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.DONE);
		order.cancel();
	}
	
	@Test
	public void shouldReturnFalseForChangingOrderDeletingPizzaAsStatusInProggress(){
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		Order spyOrder = spy(order);
		when(spyOrder.getOrderStatus()).thenReturn(OrderStatus.IN_PROGRESS);
		boolean result = spyOrder.changeOrderDeletePizza(1,2,3);
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnFalseForChangingOrderDeletingPizzaAsStatusCanceled(){
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		Order spyOrder = spy(order);
		when(spyOrder.getOrderStatus()).thenReturn(OrderStatus.CANCELED);
		boolean result = spyOrder.changeOrderDeletePizza(1,2,3);
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnTrueWhenDeleted2and3Pizzas(){
		boolean result = order.changeOrderDeletePizza(2,3);
		
		assertTrue(result);
	}
	
	@Test
	public void shouldReturnListWithout2and3Elements(){
		order.changeOrderDeletePizza(2,3);
		List<Pizza> changedPizzas = order.getPizzas();
		
		assertArrayEquals(new Integer[]{
				1,
				4,
				5
		}, new Integer[]{
				changedPizzas.get(0).getId(),
				changedPizzas.get(1).getId(),
				changedPizzas.get(2).getId()
		});
	}
	
	@Test
	public void shouldReturnFalsAsWeAddMoreAdditionPizzasResultOrderShouldHaveFrom0To10Pizzas(){
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		List<Pizza> pizzasToAdd = mock(List.class);
		
		when(pizzas.size()).thenReturn(5);
		when(pizzasToAdd.size()).thenReturn(7);
		
		boolean result = order.addPizzas(pizzasToAdd);
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnTrueAsWeAddPermitAmoutOfPizzasResultOrderShouldHaveFrom0To10Pizzas(){
		List<Pizza> pizzas = mock(List.class);
		this.order = new Order(customer, pizzas);
		List<Pizza> pizzasToAdd = mock(List.class);
		
		when(pizzas.size()).thenReturn(5);
		when(pizzasToAdd.size()).thenReturn(3);
		
		boolean result = order.addPizzas(pizzasToAdd);
		assertTrue(result);
	}

	@Test
	public void shouldReturn0AsAPriceBecauseOfEmptyListOfPizzas(){
		List<Pizza> pizzas = new ArrayList<>();
		this.order = new Order(customer, pizzas);
		double result = order.countOrderPrice();
		assertEquals(0.0, result, 0);
	}
	
	@Test
	public void shouldReturnSumOfPricePizzas(){
		double result = order.countOrderPrice();
		assertEquals(1105.0, result, 0);
	}
}
