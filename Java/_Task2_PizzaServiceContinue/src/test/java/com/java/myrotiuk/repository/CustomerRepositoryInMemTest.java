package com.java.myrotiuk.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.customer.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryContext.xml"})
@Transactional
@ActiveProfiles("dev")
public class CustomerRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	public void testInsertCastomerIntoDB(){
		Customer customer = new Customer("Ivan Myrotiuk");
		customer.setEmail("ivan@gmail.com");
		
		customerRepository.insert(customer);
		
		Assert.assertNotEquals(0, customer.getId());
	}
}
