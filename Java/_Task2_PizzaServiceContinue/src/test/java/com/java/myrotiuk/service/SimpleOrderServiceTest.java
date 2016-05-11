package com.java.myrotiuk.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Order.OrderStatus;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.exception.StatusOrderException;
import com.java.myrotiuk.exception.WrongIdOfOrderException;
import com.java.myrotiuk.service.order.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml","classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class SimpleOrderServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private OrderService orderService;
	
	@PersistenceContext
	private EntityManager em;
	
	private  Address address;
	private Order order;

	@Before
	public void setUp(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		address = new Address(customer, "Beverly Hills 17", "777777777");
		
		jdbcTemplate.execute("ALTER TABLE pizza ALTER COLUMN ID RESTART WITH 1");//"alter table pizza auto_increment = 1");
		
		Pizza pizza = new Pizza("Blue Sky", 100, Type.MEAT);
		Pizza pizza2 = new Pizza("Such a good Day", 100, Type.VEGETERIAN);
		Pizza pizza3 = new Pizza("Greate emotions", 100, Type.SEA);
		Pizza pizza4 = new Pizza("Blue sea", 300, Type.SEA);
		em.persist(pizza);
		em.persist(pizza2);
		em.persist(pizza3);
		em.persist(pizza4);
		
		Map<Pizza, Integer> pizzas = new HashMap<>();
		pizzas.put(pizza, 1);
		pizzas.put(pizza2, 1);
		pizzas.put(pizza3, 1);
		pizzas.put(pizza4, 2);
		
		order = new Order();
		order.setAddress(address);
		order.setPizzas(pizzas);
	}
	
	@Test
	public void testPlaceNewOrder() {
		Order order = orderService.placeNewOrder(address, 1,1,2,3,4,4);//(int)pizza.getId(), (int)pizza.getId(), (int)pizza2.getId(), (int)pizza3.getId(), (int)pizza4.getId(), (int)pizza4.getId());
		assertNotNull(order);
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
		assertEquals(1000, order.getOrderPrice(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWhenOrderContainMoreThan10PizzasShouldThrowException(){
		Order order = orderService.placeNewOrder(address, 1,1,2,3,4,4,4,1,2,3,4,1);
	}
	
	@Test
	public void testProcessOrder(){
		
		em.persist(order);
		
		Order processedOrder = orderService.processOrder(order.getId());
		assertNotNull(processedOrder);
		assertEquals(OrderStatus.IN_PROGRESS, processedOrder.getOrderStatus());
		assertEquals(900, processedOrder.getOrderPrice(), 0);
	}
	
	@Test(expected = StatusOrderException.class)
	public void testProcessOrderWhenStatusOrderIsNotNew(){

		order.setOrderStatus(OrderStatus.DONE);
		
		em.persist(order);
		
		Order processedOrder = orderService.processOrder(order.getId());
	}
	
	@Test(expected = WrongIdOfOrderException.class)
	public void testProcessOrderWhenWrongIdOfOrder(){
		Order processedOrder = orderService.processOrder(777);
	}
	
	@Test
	public void testCompleteOrder(){

		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		Order processedOrder = orderService.completeOrder(order.getId());
		assertNotNull(processedOrder);
		assertEquals(OrderStatus.DONE, processedOrder.getOrderStatus());
	}
	
	@Test(expected = StatusOrderException.class)
	public void testCompleteOrderWhenStatusOrderIsNotInProgress(){
		
		order.setOrderStatus(OrderStatus.CANCELED);
		
		em.persist(order);
		
		Order processedOrder = orderService.completeOrder(order.getId());
	}
	
	@Test(expected = WrongIdOfOrderException.class)
	public void testCompleteOrderWhenWrongIdOfOrder(){
		Order processedOrder = orderService.completeOrder(777);
	}
	
	

}
