package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.card.AccruedCardRepository;
import com.mysql.jdbc.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryContext.xml"})
@ActiveProfiles("dev")
@Transactional
public class AccruedCardRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private AccruedCardRepository cardRepository;
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testInsertAccCardToDB() {
		
		Customer customer = new Customer("Ivan");
		AccruedCard accruedCard = new AccruedCard();
		accruedCard.setCustomer(customer);
		
		cardRepository.insert(accruedCard);
		assertNotEquals(0, customer.getId());
		assertNotEquals(0, accruedCard.getId());
	}
	
	@Test
	public void testInsertAccCardToDBWhenCustomerIsDetached(){
		Customer customer = new Customer("Ivan");
		customer.setEmail("ivan@gmail.com");
		
		final String sql = "insert into customer(name, email) values(?, ?)";
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, customer.getName());
				stmt.setString(2, customer.getEmail());
				return stmt;
			}
		},holder);
		long id_customer = holder.getKey().longValue();
		customer.setId(id_customer);
		
		AccruedCard card = new AccruedCard(customer, "card");
		
		cardRepository.insert(card);
		
		
		assertEquals(id_customer, card.getCustomer().getId());
		assertNotEquals(0, card.getId());
	}
	
	@Test
	public void testGetAccruedCardByCustomer(){
		Customer customer = new Customer("Ivan");
		customer.setEmail("ivan@gmail.com");
		AccruedCard accruedCard = new AccruedCard();
		accruedCard.setCustomer(customer);
		accruedCard.setName("Gold");
		
		Customer customer2 = new Customer("Julia");
		customer2.setEmail("julia@gmail.com");
		AccruedCard accruedCard2 = new AccruedCard();
		accruedCard2.setCustomer(customer2);
		accruedCard2.setName("Silver");
		
		em.persist(accruedCard);
		em.persist(accruedCard2);
		
		AccruedCard accruedCardResult = cardRepository.getAccruedCardByCustomer(customer);
		AccruedCard accruedCardResult2 = cardRepository.getAccruedCardByCustomer(customer2);
		System.out.println(accruedCardResult+""+accruedCardResult2);
		assertEquals(accruedCard.getId(), accruedCardResult.getId());
		assertEquals(accruedCard2.getId(), accruedCardResult2.getId());
		assertEquals(customer.getId(), accruedCardResult.getCustomer().getId());
		assertEquals(customer.getEmail(), accruedCardResult.getCustomer().getEmail());
		assertEquals(customer2.getId(), accruedCardResult2.getCustomer().getId());
	}
	
	@Test
	public void testGetAccruedCardByCustomerWhenThereIsNoSuchCustomer(){
		Customer customer = new Customer("Ivan");
		customer.setEmail("ivan@gmail.com");
		AccruedCard accruedCard = new AccruedCard();
		accruedCard.setCustomer(customer);
		accruedCard.setName("Gold");
		
		Customer customer2 = new Customer("Julia");
		customer2.setEmail("julia@gmail.com");
		
		em.persist(accruedCard);
		em.persist(customer2);
		AccruedCard accruedCardResult = cardRepository.getAccruedCardByCustomer(customer2);
		assertNull(accruedCardResult);
	}
	
	/*@Test
	public void testInsertAccCardToDBWhenCustomerIsDetached(){
		Customer customer = new Customer("Ivan");
		customer.setLogin("ivan@gmail.com");

		customer.setId(1);
		//em.persist(customer);
		//em.merge(customer);
	//	em.flush();
//		CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);
//		customerRepository.insert(customer);
//		System.out.println("CustomerId  =>"+customer.getId());

//		final String sql = "insert into customer(name, login) values(?, ?)";
//		
//		KeyHolder holder = new GeneratedKeyHolder();
//		
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//				stmt.setString(1, customer.getName());
//				stmt.setString(2, customer.getLogin());
//				return stmt;
//			}
//		},holder);
//		customer.setId(holder.getKey().longValue());
		System.out.println("customer CCCCCCCCC"+customer);
		Customer customer2 = new Customer("Ivan Myrotiuk");
		customer2.setLogin("ivan777@gmail.com");
		//customer2.setId(7);
		em.persist(customer2);
		
		AccruedCard accruedCard = new AccruedCard();
		accruedCard.setCustomer(customer);
		cardRepository.insert(accruedCard);
		
		System.out.println("Card  =>"+accruedCard);
		assertNotEquals(0, accruedCard.getId());
	}*/

}
