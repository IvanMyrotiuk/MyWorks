package com.java.myrotiuk.service.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.repository.address.AddressRepository;
import com.java.myrotiuk.repository.customer.CustomerRepository;
import com.java.myrotiuk.service.card.AccruedCardService;

@Service
public class SimpleCustomerService implements CustomerService{

	private AddressRepository addressRepository;
	private CustomerRepository customerRepository;
	private AccruedCardService cardService;
	
	@Autowired
	public SimpleCustomerService(AddressRepository addressRepository, CustomerRepository customerRepository,
			AccruedCardService cardService) {
		this.addressRepository = addressRepository;
		this.customerRepository = customerRepository;
		this.cardService = cardService;
	}
	
	@Override
	public Customer login(String userEmail, String password) {
		Customer customer = customerRepository.findCustomerByEmail(userEmail);
		if(customer != null){//&& password == customer.getPassword()
			return customer;
		}else{
			throw new IllegalArgumentException("wrong email or password, try again");
		}
	}

	@Override
	public Address singUp(String userName, String userEmail, String userAddress, String userPhone ) {//, String userPassword, String userCheckPassword,
		if(!checkIfEmailExist(userEmail)){
			Customer customer = new Customer(userName);
			customer.setEmail(userEmail);//password
			Address address = new Address(customer, userAddress, userPhone);
			addressRepository.insert(address);
			return address;
		}else{
			throw new IllegalArgumentException("User with such email exist");
		}
	}
	
	private boolean checkIfEmailExist(String userEmail){
		Customer customer = customerRepository.findCustomerByEmail(userEmail);
		if(customer == null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public long addAddress(Customer customer, String userAddress, String userPhone) {
		Address address = new Address(customer, userAddress, userPhone);
		long id = addressRepository.insert(address);
		return id;
	}
	
	@Override
	public long giveInCardToCustomer(Customer customer, String nameOfCard) {
		return cardService.awardCard(customer, nameOfCard);
	}

	@Override
	public List<Address> getAllAddressByCustomer(Customer customer) {
		List<Address> addresses = addressRepository.getAddressesByCustomer(customer);
		return addresses;
	}
	
	public Address chooseDeliveryAddress(long id_address){
		return addressRepository.find(id_address);
	}

	@Override
	public Customer findCustomer(long id) {
		return customerRepository.find(id);
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.insert(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerRepository.update(customer);
	}
	
}
