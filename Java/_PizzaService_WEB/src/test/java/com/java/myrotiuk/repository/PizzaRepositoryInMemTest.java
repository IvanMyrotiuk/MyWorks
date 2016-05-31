package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.repository.pizza.PizzaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class PizzaRepositoryInMemTest {

	@Autowired
	private PizzaRepository pizzaRepository; 
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testFindPizzaById() {
		Pizza pizza = new Pizza("Blue Sky", 107.17, Type.SEA);
		em.persist(pizza);
		em.flush();
		long idToFind = pizza.getId();
		Pizza pizzaResult = pizzaRepository.find(idToFind);
		assertEquals(pizza, pizzaResult);
	}

}
