package com.java.myrotiuk.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

@Configuration
public class PizzaServiceConfiguration {

	@Bean
	public List<Pizza> pizzasList(){
		List<Pizza> pizzas = new ArrayList<>();
		pizzas.add(new Pizza("Good day", 101.50, Type.SEA));
		pizzas.add(new Pizza("Blue sky", 111.50, Type.MEAT));
		pizzas.add(new Pizza("Woterfall", 131.50, Type.VEGETERIAN));
		return pizzas;
	}
	
}
