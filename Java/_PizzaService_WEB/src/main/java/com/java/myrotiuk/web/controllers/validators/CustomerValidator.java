package com.java.myrotiuk.web.controllers.validators;

import org.h2.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.java.myrotiuk.domain.Customer;

@Component
public class CustomerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Customer customer = (Customer)target;
		String name = customer.getName();
		if(StringUtils.isNumber(name) || name.trim().length()<4){
			errors.rejectValue("name", "tooshort", "customer.length.name");
		}
	}

}
