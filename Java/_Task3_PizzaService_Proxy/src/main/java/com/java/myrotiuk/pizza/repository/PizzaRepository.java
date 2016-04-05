package com.java.myrotiuk.pizza.repository;

import com.java.myrotiuk.pizza.domain.Pizza;

public interface PizzaRepository {
	Pizza getPizzaByID(int id);
	void init();
}
