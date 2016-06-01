package com.java.myrotiuk.web;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.address.AddressService;
import com.java.myrotiuk.service.customer.CustomerService;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

	private static final Logger loger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private AddressService addressService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping( params = "doSingUp")
	public String singUpCustomer(Model model){
		model.addAttribute("customer", new Customer());
		return "customer/singUp";
	}
	
	@RequestMapping(params = "singup", method = RequestMethod.POST)
	public String doSingUpCustomer(@ModelAttribute("customer") @Valid Customer customer, 
			/*@ModelAttribute("address") @Valid */Address address, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			loger.info("Binding result has an error!!!!!!!!!!!!!!!!!!!!");
			return "customer/singUp";
		}
		address.setCustomer(customer);
		loger.info("Customer->>"+customer);
		loger.info("Address->>"+address);
		addressService.saveAddress(address);
		loger.info("Address->>"+address);
		return "redirect:/customer/"+address.getId();//"customer/successSingUp";
	}
	
	@RequestMapping(value = "/{addressId}")
	public String showCustomerProfile(@PathVariable String addressId, Map<String, Object> model){
		
		Address address = addressService.getAddressById(new Integer(addressId));
		model.put("address", address);
		return "customer/profile";
	}
	
	@RequestMapping(value = "/{addressId}/edit", method = RequestMethod.GET)
	public String changeCustomerInfo(@PathVariable(value = "addressId") String addressId,  Map<String, Object> model){
		
		Address address = addressService.getAddressById(new Long(addressId));
		//Customer customer = customerService.findCustomer(new Long(addrId));
		model.put("address", address);
		return "customer/edit";
	}
	
	@RequestMapping(value = "/{addressId}", method = RequestMethod.POST)
	public String updateAdderss(@ModelAttribute("address") @Valid Address address, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "customer/edit";
		}
		addressService.updateAddress(address);
		return "redirect:/customer/"+address.getId();
	}
}
