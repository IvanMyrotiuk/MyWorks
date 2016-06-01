package com.java.myrotiuk.service.customer;

import java.util.List;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;

public interface CustomerService {
	
	Customer login(String userEmail, String password);
	
	Address singUp(String userName, String userEmail, String userAddress, String userPhone);//, String userPassword, String userCheckPassword,
	
	long addAddress(Customer customer, String userAddress, String userPhone);
	
	long giveInCardToCustomer(Customer customer, String nameOfCard);
	
	List<Address> getAllAddressByCustomer(Customer customer);
	
	Address chooseDeliveryAddress(long id_address);
	
	Customer findCustomer(long id);
}
