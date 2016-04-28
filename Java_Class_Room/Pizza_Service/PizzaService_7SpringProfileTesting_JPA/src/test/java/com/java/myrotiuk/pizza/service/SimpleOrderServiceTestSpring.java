package com.java.myrotiuk.pizza.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/appContext.xml", "classpath:/repositoryTestContext.xml", "classpath:/repositoryContext.xml"},inheritInitializers = true)
//@ActiveProfiles(profiles = "prod")
@ContextConfiguration(locations = {"classpath:/appContext.xml"},inheritInitializers = true)
public class SimpleOrderServiceTestSpring extends RepositoryTestConfig{

//	@Before
//	public void setUp() throws Exception {
//	}
	
	
	@Autowired
	private Customer customer;// = new Customer();
	
	@Autowired
	private OrderService orderService;

	@Test
	public void testPlaceNewOrder() {
		System.out.println("placeNewOrdernted");
		//Customer customer = null;
		Integer[] pizzasID = new Integer[]{1};
		//SimpleOrderService orderService = null;
		Order result = orderService.placeNewOrder(customer, pizzasID);
		System.out.println("test result=>"+result);
		
	}

}
