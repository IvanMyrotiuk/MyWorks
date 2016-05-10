package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.address.AddressRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryContext.xml"})//,"classpath:repositoryH2Context.xml"})//
@ActiveProfiles("dev")
@Transactional
public class AddressRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private AddressRepository addressRepository;
	
	@Test
	public void testInsertAddressToDB() {
		
		Address address = new Address(new Customer("Ivan Myrotiuk"), "Beverly Hills 17", "7777777");
		addressRepository.insert(address);
		
		long id = address.getId();
		long id_customer = address.getCustomer().getId();
		
		assertNotEquals(0, id_customer);
		assertNotEquals(0, id);
	}

}
