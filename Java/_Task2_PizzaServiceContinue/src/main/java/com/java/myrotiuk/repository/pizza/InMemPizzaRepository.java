package com.java.myrotiuk.repository.pizza;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.Pizza;

@Repository("pizzaRepositoryy")
//@Qualifier("pizzaRepository")
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

	@Override
	public List<Pizza> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Pizza pizza) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(Pizza pizza) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
