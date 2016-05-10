package com.java.myrotiuk.service;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.customer.CustomerService;
import com.mysql.jdbc.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml","classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class SimpleCustomerServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private CustomerService customerService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testSingUp() {
		
		customerService.singUp("Ivan Myrotiuk", "ivan@gmail.com", "Beverly Hills 17", "17177777");
		
		Query query = em.createQuery("select a from Address a where a.customer.email = :customerEmail");
		query.setParameter("customerEmail", "ivan@gmail.com");
		
		List<Address> addresses = query.getResultList();
		
		Address address = addresses.get(0);
		
		assertNotNull(address);
		assertEquals("Beverly Hills 17", address.getAddress());
		assertEquals("17177777", address.getPhoneNumber());
		assertEquals("ivan@gmail.com", address.getCustomer().getEmail());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSingUpWhenUserWithSuchEmailExist(){
		customerService.singUp("Ivan Myrotiuk", "ivan@gmail.com", "Beverly Hills 17", "17177777");
		customerService.singUp("Julia Myrotiuk", "ivan@gmail.com", "Beverly Hills 17", "22222222");
	}
	
	@Test
	public void testLogin(){
		
		String name = "Ivan Myrotiuk";
		String email = "ivan@gmail.com";
		
		String sql = "insert into customer(name, email) values(?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.setString(2, email);
				return stmt;
			}
		}, holder);
		
		Customer customer = customerService.login("ivan@gmail.com", "");
		assertNotNull(customer);
		assertEquals("ivan@gmail.com", customer.getEmail());
		assertNotEquals(0, holder.getKey().longValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLoginWhenThereIsNoSuchCustomer(){
		Customer customer = customerService.login("julia@gmail.com", "");
	}
	
	@Test
	public void testAddingAddresses(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		customerService.addAddress(customer, "Beverly Hills 17", "77777777");
		customerService.addAddress(customer, "Beverly Hills 77", "33333333");
		
		Query query = em.createQuery("select a from Address a where a.customer.email = :userEmail");
		query.setParameter("userEmail", customer.getEmail());
		List<Address> addresses = query.getResultList();
		
		assertEquals(2, addresses.size());
	}
	
	@Test
	public void testGetAllAddressByCustomer(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		Address address1 = new Address(customer, "Beverly Hills 17", "7777777");
		Address address2 = new Address(customer, "Maimi 17 ", "3333333");
		
		em.persist(address1);
		em.persist(address2);
		
		List<Address> addresses = customerService.getAllAddressByCustomer(customer);
		
		assertArrayEquals(new Address[]{address1, address2}, addresses.toArray());
	}
	
	@Test
	public void testChooseDeliveryAddress(){
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		Address address1 = new Address(customer, "Beverly Hills 17", "7777777");
		Address address2 = new Address(customer, "Maimi 17 ", "3333333");
		
		em.persist(address1);
		em.persist(address2);
		
		Address address = customerService.chooseDeliveryAddress(address2.getId());
		
		assertEquals(address2, address);
	}
	
	@Test
	public void testGiveInCardToCustomer(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		em.persist(customer);
		
		long id_card = customerService.giveInCardToCustomer(customer, "Super Card");
		
		Query query = em.createQuery("select c from AccruedCard c where c.customer = :customer");
		query.setParameter("customer", customer);
		List<AccruedCard> card = query.getResultList();
		
		assertEquals(id_card, card.get(0).getId());
	}
	

}
