package com.java.myrotiuk.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.service.discount.DiscountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml","classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class SimpleDiscountServiceTest {

	@Autowired
	private DiscountService discountService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testGetDiscountForMoreThan4PizzasAndCardDiscount() {
		
		Pizza pizza = new Pizza("Blue Sky", 117.7, Type.MEAT);
		Pizza pizza2 = new Pizza("Such a good Day", 107.7, Type.VEGETERIAN);
		Pizza pizza3 = new Pizza("Greate emotions", 100, Type.SEA);
		Pizza pizza4 = new Pizza("Blue sea", 300, Type.SEA);
		
		em.persist(pizza);
		em.persist(pizza2);
		em.persist(pizza3);
		em.persist(pizza4);//for getting id else map will put into hash by 0 all elements and finally there will be just 1 element
		
		Map<Pizza, Integer> pizzas = new HashMap<>();
		pizzas.put(pizza, 1);
		pizzas.put(pizza2, 1);
		pizzas.put(pizza3, 1);
		pizzas.put(pizza4, 2);
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		
		Address address = new Address(customer, "Beverly Hills 17", "7777777777");
		
		AccruedCard accruedCard = new AccruedCard(customer, "Super Gold card");
		accruedCard.setAmount(300);
		em.persist(accruedCard);
		
		Order order = new Order();
		order.setAddress(address);
		order.setPizzas(pizzas);
		
		double resultDiscount = discountService.getDiscount(order);
		
		assertEquals(120, resultDiscount, 0);
	}
	
	public void testGetDiscountForLessThan4PizzasAndDiscountFromOrder() {
		
		Pizza pizza = new Pizza("Blue Sky", 117.7, Type.MEAT);
		Pizza pizza2 = new Pizza("Such a good Day", 107.7, Type.VEGETERIAN);
		Pizza pizza3 = new Pizza("Greate emotions", 100, Type.SEA);
		Pizza pizza4 = new Pizza("Blue sea", 300, Type.SEA);
		
		em.persist(pizza);
		em.persist(pizza2);
		em.persist(pizza3);
		em.persist(pizza4);
		
		Map<Pizza, Integer> pizzas = new HashMap<>();
		pizzas.put(pizza, 1);
		pizzas.put(pizza4, 2);
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		
		Address address = new Address(customer, "Beverly Hills 17", "7777777777");
		
		AccruedCard accruedCard = new AccruedCard(customer, "Super Gold card");
		accruedCard.setAmount(10000);
		em.persist(accruedCard);
		
		Order order = new Order();
		order.setAddress(address);
		order.setPizzas(pizzas);
		
		double resultDiscount = discountService.getDiscount(order);
		
		assertEquals(215.31, resultDiscount, 0);
	}
}
