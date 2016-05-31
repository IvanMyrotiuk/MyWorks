package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.repository.order.OrderRepository;
import com.mysql.jdbc.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class OrderRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private OrderRepository orderRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	private Order order;
	
	@Before
	public void setUp(){
		Address address = new Address(new Customer("Ivan Myrotiuk"), "Beverly Hills 17", "7777777");
		Pizza pizza = new Pizza("Blue Sky", 117.17, Type.MEAT);
		Pizza pizza2 = new Pizza("Blue Sea", 107, Type.SEA);
		em.persist(pizza);
		em.persist(pizza2);
		Map<Pizza, Integer> pizzas = new HashMap<>();
		pizzas.put(pizza, 1);
		pizzas.put(pizza2, 2);
		
		order = new Order();
		order.setPizzas(pizzas);
		order.setAddress(address);
		
		
	}
	
	@Test
	public void testGetOrderById() {

		em.persist(order);
		
		Long id_order = order.getId();
		
		Optional<Order> orderResultFromDB = orderRepository.getOrder(id_order);
		Order orderResult = orderResultFromDB.get();
		assertEquals(order, orderResult);
	}
	
	@Test
	public void testGetOrderByIdIfThereIsNoSuchOrder() {
		
		Optional<Order> orderResultFromDB = orderRepository.getOrder(777);

		assertFalse(orderResultFromDB.isPresent());
	}
	
	@Test
	public void testUpdateOrder(){

		em.persist(order);
		
		Pizza pizza3 = new Pizza("Such a good Day!!", 177, Type.VEGETERIAN);
		em.persist(pizza3);

		order.addPizzas(Arrays.asList(pizza3));
		orderRepository.update(order);

		Long id_order = order.getId();

		Optional<Order> orderResultFromDB = orderRepository.getOrder(id_order);
		Order orderResult = orderResultFromDB.get();
		assertEquals(order, orderResult);
		assertTrue(orderResult.getPizzas().containsKey(pizza3));
	}
	
	@Test
	public void testInsertOrderIntoDB(){
		
		em.persist(order);
		
		long id_order = order.getId();
		assertNotEquals(0, id_order);
	}
	
	@Test
	public void testInsertOrderIntoDBWhenAddressIsDetached(){
	
		Customer customer = new Customer("Ivan");
		customer.setEmail("ivan777@gmail.com");
		String sqlCustomer = "insert into Customer(name, email) values(?, ?)";
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sqlCustomer, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, customer.getName());
				stmt.setString(2, customer.getEmail());
				return stmt;
			}
		},holder);
		customer.setId(holder.getKey().longValue());
		
		Address address = new Address();
		address.setAddress("Beverly Hills 777");
		address.setCustomer(customer);
		address.setPhoneNumber("222222222");
		
		String sqlAddress = "insert into Address(customer_id, address, phoneNumber) values(?,?,?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sqlAddress, Statement.RETURN_GENERATED_KEYS);
				stmt.setLong(1, address.getCustomer().getId());
				stmt.setString(2, address.getAddress());
				stmt.setString(3, address.getPhoneNumber());
				return stmt;
			}
		},holder);
		
		address.setId(holder.getKey().longValue());
		
		order.setAddress(address);
		
		orderRepository.insert(order);
		assertNotEquals(0, order.getId());
	}
}
