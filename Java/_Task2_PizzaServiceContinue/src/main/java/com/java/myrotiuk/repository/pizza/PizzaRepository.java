package com.java.myrotiuk.repository.pizza;

import java.util.List;

import com.java.myrotiuk.domain.Pizza;

public interface PizzaRepository {
	Pizza getPizzaByID(int id);
	List<Pizza> getAll();
	void update(Pizza pizza);
	int insert(Pizza pizza);
	void delete(int id);
}
