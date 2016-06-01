package com.java.myrotiuk.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.repository.address.AddressRepository;

@Service
public class SimpleAddressService implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public void saveAddress(Address address) {
		addressRepository.insert(address);
	}

	@Override
	public Address getAddressById(long id) {
		return addressRepository.find(id);
	}

	@Override
	public void updateAddress(Address address) {
		addressRepository.update(address);
	}

}
