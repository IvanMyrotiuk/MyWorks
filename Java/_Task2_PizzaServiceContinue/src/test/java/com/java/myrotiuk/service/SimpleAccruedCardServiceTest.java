package com.java.myrotiuk.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.card.AccruedCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml","classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class SimpleAccruedCardServiceTest {

	@Autowired
	private AccruedCardService accruedCardService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testFindCardByCustomer() {
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		AccruedCard accruedCard = new AccruedCard(customer, "Super Card");
		
		em.persist(accruedCard);
		
		Optional<AccruedCard> optAccCard = accruedCardService.findCardByCustomer(customer);
		
		assertEquals(accruedCard.getId(), optAccCard.get().getId());
	}
	
	@Test
	public void testFindCardByCustomerWhenThereIsNoSuchCustomer(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		em.persist(customer);
		
		Optional<AccruedCard> optAccCard = accruedCardService.findCardByCustomer(customer);
		assertFalse(optAccCard.isPresent());
	}
	
	@Test
	public void testUpdateCard(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		AccruedCard accruedCard = new AccruedCard(customer, "Super Card");
		
		em.persist(accruedCard);
		
		accruedCard.setAmount(777);
		
		accruedCardService.updateCard(accruedCard);
		
		Query query = em.createQuery("select c from AccruedCard c where c.id = ?1");
		query.setParameter(1, accruedCard.getId());
		
		List<AccruedCard> accCard = query.getResultList();
		
		assertEquals(777, accCard.get(0).getAmount(), 0);
	}
	
	@Test
	public void testAwardCard(){
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		em.persist(customer);//TransientObjectException if comment it
		
		long id_card = accruedCardService.awardCard(customer, "Super Card");
		
		Query query = em.createQuery("select c from AccruedCard c where c.customer.id = ?1");
		query.setParameter(1, customer.getId());
		
		List<AccruedCard> card = query.getResultList();
		
		assertEquals(id_card, card.get(0).getId());
	}
	
	@Test//CheckReturn return the same card
	public void testAwardCardIfCardIsPresent(){
		
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		AccruedCard accruedCard = new AccruedCard(customer, "Super Gold Card");
		
		em.persist(accruedCard);
		//em.flush();//It is not in BD as strategy of generating keys is sequence It has already generated key but has not been saved into DB . Without flashing it will be saved into DB after closing transaction
		long id_card = accruedCardService.awardCard(customer, "Super Silver Card");
		Query query = em.createQuery("select c from AccruedCard c where c.id = ?1");
		query.setParameter(1, id_card);
		
		List<AccruedCard> card = query.getResultList();
		
		assertEquals(accruedCard.getId(), id_card);
		assertEquals(accruedCard.getName(), card.get(0).getName());
		assertNotEquals(accruedCard.getName(), "Super Silver Card");
	}
}
