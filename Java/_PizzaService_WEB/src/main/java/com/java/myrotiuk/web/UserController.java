package com.java.myrotiuk.web;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.customer.CustomerRepository;
import com.java.myrotiuk.service.customer.CustomerService;
import com.java.myrotiuk.web.controllers.validators.CustomerValidator;

@Controller
//@RequestMapping(value = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerValidator customerValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(customerValidator);
	}
	
	@RequestMapping(value = "/user", params = "singup", method = RequestMethod.GET)
	public ModelAndView singUpUser(){
		return new ModelAndView("user/singUp","user", new Customer());
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String doSingUp(@ModelAttribute("user") @Valid Customer customer, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "user/singUp";
		}
		customerService.saveCustomer(customer);
		return "redirect:/users/"+customer.getId();
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelAndView showUserProfile(@PathVariable(value = "id") String id, Model model){
		return new ModelAndView("user/profile","user", customerService.findCustomer(new Long(id)));
	}
	
	@RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable("id") String id){//String, Model model
		return new ModelAndView("user/edit", "user", customerService.findCustomer(new Long(id)));
//		Customer customer = customerService.findCustomer(new Long(id));
//		model.addAttribute("user", customer);
//		return "user/edit";
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") @Valid Customer customer, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "user/edit";
		}
		customerService.updateCustomer(customer);
		return "redirect:/users/"+customer.getId();
	}
}
