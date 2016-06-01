package com.java.myrotiuk.service.address;

import com.java.myrotiuk.domain.Address;

public interface AddressService {
	void saveAddress(Address address);
	Address getAddressById(long id);
	void updateAddress(Address address);
}
