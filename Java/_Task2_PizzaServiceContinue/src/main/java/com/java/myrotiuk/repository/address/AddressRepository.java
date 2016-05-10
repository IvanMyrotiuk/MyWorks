package com.java.myrotiuk.repository.address;

import java.util.List;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.BaseRepository;

public interface AddressRepository extends BaseRepository<Address>{
	List<Address> getAddressesByCustomer(Customer customer);
}
