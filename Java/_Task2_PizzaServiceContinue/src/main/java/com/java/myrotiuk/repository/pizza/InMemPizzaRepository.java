package com.java.myrotiuk.repository.pizza;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.Pizza;

@Repository("pizzaRepository")
public class InMemPizzaRepository implements PizzaRepository {

	private List<Pizza> pizzas; //= new ArrayList<>();
	{
		
	}
	
	@Resource(name = "pizzasList")
	public void setPizzas(List<Pizza> pizzas){
		this.pizzas = pizzas;
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
