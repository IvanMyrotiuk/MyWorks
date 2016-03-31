package com.java.myrotiuk.repository.pizza;

import java.util.ArrayList;
import java.util.List;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

public class InMemPizzaRepository implements PizzaRepository {

	private List<Pizza> pizzas = new ArrayList<>();
	{
		pizzas.add(new Pizza("Good day", 101.50, Type.SEA));
		pizzas.add(new Pizza("Blue sky", 111.50, Type.MEAT));
		pizzas.add(new Pizza("Woterfall", 131.50, Type.VEGETERIAN));
	}
	@Override
	public Pizza getPizzaByID(int id){
    	for(Pizza pizza: pizzas){
    		if(pizza.getId() == id)
    			return pizza;
    	}
    	return null;
    }

}
