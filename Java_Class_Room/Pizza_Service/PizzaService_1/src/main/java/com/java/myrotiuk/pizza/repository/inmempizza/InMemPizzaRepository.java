package com.java.myrotiuk.pizza.repository.inmempizza;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.domain.Pizza.Type;
import com.java.myrotiuk.pizza.repository.PizzaRepository;

public class InMemPizzaRepository implements PizzaRepository {

	private List<Pizza> pizzas = new ArrayList<Pizza>();
	{
		pizzas.add(new Pizza(1, "Tomato", 100.2, Type.VEGETERIAN));
        pizzas.add(new Pizza(2, "Meat", 99.0, Type.MEAT));
        pizzas.add(new Pizza(3, "Blue Sea", 100.2, Type.SEA));
	}
	
	public Pizza getPizzaByID(int id) {
		 int order = id - 1;
		 return pizzas.get(order);
	}

}
