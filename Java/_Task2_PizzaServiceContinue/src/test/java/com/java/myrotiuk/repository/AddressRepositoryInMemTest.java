package com.java.myrotiuk.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.address.AddressRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repositoryH2Context.xml"})
public class AddressRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private AddressRepository addressRepository;
	
	@Test
	public void testInsertAddressToDB() {
		
		Address address = new Address(new Customer("Ivan"), "Beverly Hills", "7777777");
		addressRepository.insert(address);
		
		long id = address.getId();
		System.out.println(id);
		assertNotEquals(0, id);
	}

}
