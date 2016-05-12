package com.java.myrotiuk.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.exception.StatusOrderException;

public class OrderTest {

	private Order order;
	private Address address;
	
	@Before
	public void setUp() throws Exception {
		
		Map<Pizza, Integer> pizzas = mock(Map.class);
		this.order = new Order();
		order.setAddress(address);
		order.setPizzas(pizzas);
	}

	@Test
	public void shoulrReturnOrderWithInProgressStatus() {
		Order orderToCheck = order.next();
		OrderStatus orderStatus = orderToCheck.getOrderStatus();
		assertEquals(OrderStatus.IN_PROGRESS, orderStatus); 
	}
	
	@Test
	public void shoulrReturnOrderWithCanceledStatus() {
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
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		boolean result = order.changeOrderDeletePizza(1,2,3);
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnFalseForChangingOrderDeletingPizzaAsStatusCanceled(){
		
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
	public void shouldDeleteAllPizzasInOrderBySpecificId(){
		Map<Pizza, Integer> pizzas = new HashMap<>();

		Pizza pizza = new Pizza(1,"Good day", 100, Type.SEA);
		pizzas.put(pizza, 1);
		Pizza pizza2 = new Pizza(2,"Blue sky", 200, Type.MEAT);
		pizzas.put(pizza2, 1);
		Pizza pizza3 = new Pizza(3,"Woterfall", 255, Type.VEGETERIAN);
		pizzas.put(pizza3, 3);
		Pizza pizza4 = new Pizza(4,"Woterfall2", 300, Type.VEGETERIAN);
		pizzas.put(pizza4, 1);
		Pizza pizza5 = new Pizza(5,"Woterfall3", 250, Type.VEGETERIAN);
		pizzas.put(pizza5, 2);
		
		order.setPizzas(pizzas);
		
		order.changeOrderDeletePizza(2,3);
		
		Map<Pizza, Integer> changedPizzas = order.getPizzas();
		
		assertEquals(pizzas.get(pizza), new Integer(1));
		assertNull(pizzas.get(pizza2));
		assertNull(pizzas.get(pizza3));
		assertEquals(pizzas.get(pizza4), new Integer(1));
		assertEquals(pizzas.get(pizza5), new Integer(2));
	}
	
	@Test
	public void testAddPizzaToOrder(){
		Map<Pizza, Integer> pizzas = new HashMap<>();
		Pizza pizza = new Pizza(1,"Good day", 100, Type.SEA);
		pizzas.put(pizza, 1);
		Pizza pizza2 = new Pizza(2,"Blue sky", 200, Type.MEAT);
		pizzas.put(pizza2, 1);
		Pizza pizza3 = new Pizza(3,"Woterfall", 255, Type.VEGETERIAN);
		pizzas.put(pizza3, 3);
		Pizza pizza4 = new Pizza(4,"Woterfall2", 300, Type.VEGETERIAN);
		pizzas.put(pizza4, 1);
		Pizza pizza5 = new Pizza(5,"Woterfall3", 250, Type.VEGETERIAN);
		pizzas.put(pizza5, 2);
		order.setPizzas(pizzas);
		order.addPizzas(Arrays.asList(pizza, pizza2, pizza3, pizza4));
		
		assertEquals(pizzas.get(pizza), new Integer(2));
		assertEquals(pizzas.get(pizza2), new Integer(2));
		assertEquals(pizzas.get(pizza3), new Integer(4));
		assertEquals(pizzas.get(pizza4), new Integer(2));
		assertEquals(pizzas.get(pizza5), new Integer(2));
	}

	@Test
	public void shouldReturn0AsAPriceBecauseOfEmptyListOfPizzas(){
		double result = order.countOrderPrice();
		assertEquals(0.0, result, 0);
	}
	
	@Test
	public void shouldReturnSumOfPricePizzas(){
		Map<Pizza, Integer> pizzas = new HashMap<>();
		
		
		
		Pizza pizza = new Pizza(1,"Good day", 100, Type.SEA);
		pizzas.put(pizza, 1);
		Pizza pizza2 = new Pizza(2,"Blue sky", 200, Type.MEAT);
		pizzas.put(pizza2, 1);
		Pizza pizza3 = new Pizza(3,"Woterfall", 255, Type.VEGETERIAN);
		pizzas.put(pizza3, 1);
		Pizza pizza4 = new Pizza(4,"Woterfall2", 300, Type.VEGETERIAN);
		pizzas.put(pizza4, 1);
		Pizza pizza5 = new Pizza(5,"Woterfall3", 250, Type.VEGETERIAN);
		pizzas.put(pizza5, 2);
		
		order.setPizzas(pizzas);
		order.setOrderStatus(OrderStatus.DONE);
		double result = order.countOrderPrice();
		assertEquals(1355.0, result, 0);
	}
}
