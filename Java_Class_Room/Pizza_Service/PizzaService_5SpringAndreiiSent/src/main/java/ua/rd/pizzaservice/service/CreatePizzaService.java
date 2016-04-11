package ua.rd.pizzaservice.service;

import ua.rd.pizzaservice.domain.Pizza;

/**
 *
 * @author andrii
 */
public class CreatePizzaService {
    public Pizza createPizza(Integer id, String name, Double price, Pizza.PizzaType type) {
        return new Pizza(id, name, price, type);
    }
}
