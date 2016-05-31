package com.java.myrotiuk.repository.customer;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.BaseRepository;

public interface CustomerRepository extends BaseRepository<Customer> {
	Customer findCustomerByEmail(String email);
}
