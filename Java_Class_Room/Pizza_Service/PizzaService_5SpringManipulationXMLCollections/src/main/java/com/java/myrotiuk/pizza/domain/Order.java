package com.java.myrotiuk.pizza.domain;

import java.util.List;

public class Order {
	private Long id;
	private Customer customer;
	private List<Pizza> pizzas;
	private static long currentId = 0l;
	public Order(Long id, Customer customer, List<Pizza> pizzas) {
		this.id = id;
		this.customer = customer;
		this.pizzas = pizzas;
	}
	
	public Order(Customer customer, List<Pizza> pizzas) {
		this.customer = customer;
		this.pizzas = pizzas;
		this.id = ++currentId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", pizzas=" + pizzas + "]";
	}
}
