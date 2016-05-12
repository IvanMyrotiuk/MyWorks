package com.java.myrotiuk.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.AccruedCard;
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
	private Pizza pizza;
	private Pizza pizza2;
	private Pizza pizza3;
	private Pizza pizza4;

	@Before
	public void setUp(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		address = new Address(customer, "Beverly Hills 17", "777777777");
		
		jdbcTemplate.execute("ALTER TABLE pizza ALTER COLUMN ID RESTART WITH 1");//"alter table pizza auto_increment = 1");
		
		pizza = new Pizza("Blue Sky", 100, Type.MEAT);
		pizza2 = new Pizza("Such a good Day", 100, Type.VEGETERIAN);
		pizza3 = new Pizza("Greate emotions", 100, Type.SEA);
		pizza4 = new Pizza("Blue sea", 300, Type.SEA);
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
	public void testCompleteOrderWithDiscountForTheExpensiestPizza(){

		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		Order processedOrder = orderService.completeOrder(order.getId());
		assertNotNull(processedOrder);
		assertEquals(OrderStatus.DONE, processedOrder.getOrderStatus());
		assertEquals(810, processedOrder.getOrderPrice(), 0);
	}
	
	@Test
	public void testCompleteOrderWithDiscountForTheExpensiestPizzaAnd10PercentFromCard(){

		AccruedCard card = new AccruedCard(order.getAddress().getCustomer(), "Super Card");
		card.setAmount(200);
		
		em.persist(card);
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		Order processedOrder = orderService.completeOrder(order.getId());
		assertNotNull(processedOrder);
		assertEquals(OrderStatus.DONE, processedOrder.getOrderStatus());
	}
	
	@Test
	public void testCompleteOrderWithDiscountForTheExpensiestPizzaAnd30PercentFromOrderPriceAs10PercentMoreThan30PercentFromOrderPrice(){
		//from order price = 900
		AccruedCard card = new AccruedCard(order.getAddress().getCustomer(), "Super Card");
		card.setAmount(10000);
		
		em.persist(card);
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		Order processedOrder = orderService.completeOrder(order.getId());
		assertNotNull(processedOrder);
		assertEquals(OrderStatus.DONE, processedOrder.getOrderStatus());
		assertEquals(540, processedOrder.getOrderPrice(), 0);
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
	
	@Test(expected = WrongIdOfOrderException.class)
	public void testPayWhenWrongIdOfOrder(){
		double orderPrice = orderService.pay(777);
	}
	
	@Test(expected = StatusOrderException.class)
	public void testPayWhenStatusIsnotDone(){
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		double orderPrice = orderService.pay(order.getId());
	}
	
	@Test
	public void testPayToCheckIfCardWasGivenToCustomerFirstlyButItWasNotAccruedToTheCardPriceForOrder(){
		
		order.setOrderStatus(OrderStatus.DONE);
		
		em.persist(order);
		
		orderService.pay(order.getId());
		
		Address address = order.getAddress();
		
		Query query = em.createQuery("select c from AccruedCard c where c.customer = :customer");
		query.setParameter("customer", address.getCustomer());
		
		List<AccruedCard> card = query.getResultList();
		
		assertNotNull(card.get(0));
		assertEquals(0, card.get(0).getAmount(), 0);
	}
	
	@Test
	public void testPayToCheckIfItWasAccruedToTheCardPriceForOrder(){
		
		order.setOrderStatus(OrderStatus.DONE);
		
		Address address = order.getAddress();
		
		AccruedCard card = new AccruedCard(address.getCustomer(), "Super Gold Card");
		
		em.persist(card);
		em.persist(order);
		
		orderService.pay(order.getId());
		
		Query query = em.createQuery("select c from AccruedCard c where c.customer = :customer");
		query.setParameter("customer", address.getCustomer());
		
		List<AccruedCard> cardToCheck = query.getResultList();
		
		assertNotNull(cardToCheck.get(0));
		assertEquals(900, cardToCheck.get(0).getAmount(), 0);
	}
	
	@Test
	public void testCancelOrder(){
		
		order.setOrderStatus(OrderStatus.NEW);//by default
		
		em.persist(order);
		
		Order canceledOrder = orderService.cancelOrder(order.getId());
		
		assertNotNull(canceledOrder);
		assertEquals(OrderStatus.CANCELED, canceledOrder.getOrderStatus());

	}
	
	@Test(expected = WrongIdOfOrderException.class)
	public void testCancelOrderWhenThereIsNoSuchIdOfOrder(){
		
		Order canceledOrder = orderService.cancelOrder(777);
	}
	
	@Test
	public void testAddPizzaToOrderWhenQuantityOfPizzasUpTo10AndOrderStatusNew(){
		
		order.setOrderStatus(OrderStatus.NEW);
		
		em.persist(order);
		
		boolean resultOfAdding= orderService.addPizzaToOrder(order.getId(), 1, 2, 2);
		
		Map<Pizza, Integer> pizzas = order.getPizzas();
		
		assertEquals(pizzas.get(pizza), new Integer(2));
		assertEquals(pizzas.get(pizza2), new Integer(3));
		assertEquals(pizzas.get(pizza3), new Integer(1));
		assertEquals(pizzas.get(pizza4), new Integer(2));
		assertTrue(resultOfAdding);
	}
	
	@Test
	public void testAddPizzaToOrderWhenQuantityOfPizzasMoreThen10AndOrderStatusNew(){
		
		order.setOrderStatus(OrderStatus.NEW);
		
		em.persist(order);
		
		boolean resultOfAdding= orderService.addPizzaToOrder(order.getId(), 1, 2, 2,4,2,1,3,2);
		assertFalse(resultOfAdding);
	}
	
	@Test
	public void testAddPizzaToOrderWhenAtAnotherStatusThanNew(){
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		
		em.persist(order);
		
		boolean resultOfAdding= orderService.addPizzaToOrder(order.getId(), 1, 2, 2);
		assertFalse(resultOfAdding);
	}
	
	@Test
	public void testAddPizzaToOrderWhenWrongIdOfOrder(){
		boolean resultOfAdding= orderService.addPizzaToOrder(777, 1, 2, 2);
		assertFalse(resultOfAdding);
	}
	
	
	@Test
	public void testChangeOrderDeletePizzaAllByIdOfPizza(){
		
		order.setOrderStatus(OrderStatus.NEW);
		
		em.persist(order);
		
		boolean resultOfAdding= orderService.changeOrderDeletePizza(order.getId(), 1, 2, 4);
		
		Map<Pizza, Integer> pizzas = order.getPizzas();
		
		assertNull(pizzas.get(pizza));
		assertNull(pizzas.get(pizza2));
		assertEquals(pizzas.get(pizza3), new Integer(1));
		assertNull(pizzas.get(pizza4));
		assertTrue(resultOfAdding);
	}
	
	public void testChangeOrderDeletePizzaAllByIdOfPizzaWhenWrongId(){
		
		boolean resultOfAdding= orderService.changeOrderDeletePizza(777, 1, 2, 4);
	
		assertFalse(resultOfAdding);
	}
	
}
