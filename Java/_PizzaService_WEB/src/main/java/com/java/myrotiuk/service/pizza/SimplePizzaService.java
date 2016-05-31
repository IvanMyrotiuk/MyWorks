package com.java.myrotiuk.service.pizza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.repository.pizza.PizzaRepository;

@Service
public class SimplePizzaService implements PizzaService {

	private PizzaRepository pizzaRepository;
	
	@Autowired
	public SimplePizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}
	
	@Override
	public List<Pizza> getAllPizzas() {
		return pizzaRepository.getAll();
	}

}
