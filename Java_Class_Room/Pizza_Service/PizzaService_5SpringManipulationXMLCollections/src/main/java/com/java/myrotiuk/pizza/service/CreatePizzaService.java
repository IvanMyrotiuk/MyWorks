package com.java.myrotiuk.pizza.service;

import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.domain.Pizza.Type;

public class CreatePizzaService {
	public Pizza createPizza(Integer id, String name, Double price, Type type){
		return new Pizza(id, name, price, type);
	}
}
