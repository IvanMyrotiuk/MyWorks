package com.java.myrotiuk.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.address.AddressService;

@Controller
public class CustomerController {

	private static final Logger loger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = "/customer", params = "doSingUp")
	public String singUpCustomer(){
		return "customer/singUp";
	}
	
	@RequestMapping(value = "/customer", params = "singup", method = RequestMethod.POST)
	public String doSingUpCustomer(Customer customer, Address address){
		address.setCustomer(customer);
		loger.info("Customer->>"+customer);
		loger.info("Address->>"+address);
		addressService.saveAddress(address);
		return "redirect:/customer/"+address.getId();//"customer/successSingUp";
	}
	
	@RequestMapping(value = "/customer/{addressId}")
	public String showCustomerProfile(@PathVariable String addressId, Map<String, Object> model){
		
		Address address = addressService.getAddressById(new Integer(addressId));
		model.put("address", address);
		return "customer/profile";
	}
	
}
