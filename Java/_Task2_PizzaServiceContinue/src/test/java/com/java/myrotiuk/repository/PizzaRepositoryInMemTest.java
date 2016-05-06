package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.myrotiuk.repository.pizza.PizzaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryH2Context.xml"})
public class PizzaRepositoryInMemTest {

	@Autowired
	private PizzaRepository pizzaRepository; 
	
	@Test
	public void test() {
		
	}

}
