package com.java.myrotiuk.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Order {
	private Long id;
	private Customer customer;
	private List<Pizza> pizzas;
	private Status status;
	private static long currentId = 0l;

	private double price = 0;
	
	public enum Status{
		NEW, IN_PROGRSS, CANCELED, DONE;
	}
	
	public Order(){
		
	}
	
	public Order(Customer customer){
		this.customer = customer;
		this.id = ++currentId;
	}
	
	public Order(Customer customer, List<Pizza> pizzas) {
		this.customer = customer;
		this.pizzas = pizzas;
		this.status = Status.NEW;
		this.price = countPrice();
	}

	
	public boolean setStatusToProgress(Order order){
		if(order.status == Status.NEW){
			this.status = Status.IN_PROGRSS;
			return true;
		}
		return false;
	}
	
	public boolean setStatusToCancel(Order order){
		if(order.status == Status.IN_PROGRSS || order.status == Status.NEW){
			this.status = Status.CANCELED;
			return true;
		}
		return false;
	}
	
	public boolean setStatusToDone(Order order){
		if(order.status == Status.IN_PROGRSS){
			this.status = Status.DONE;
			return true;
		}
		return false;
	}
	
	private double countPrice(){
		double result = 0;
		if(pizzas.size() > 4){
			Pizza pizzaMax = Collections.max(pizzas, new Comparator<Pizza>(){
				public int compare(Pizza z1, Pizza z2){
					return (int) (z1.getPrice() - z2.getPrice());
				}
			});
			pizzaMax.setPrice(pizzaMax.getPrice() - pizzaMax.getPrice()*30/100);
		}
		
		for(Pizza pizza : pizzas){
			result = result + pizza.getPrice();
		}
		return result;
	}
	
	//private 
	
	public Status getStatus() {
		return status;
	}



//	public void setStatus(Status status) {
//		this.status = status;
//	}



	public Long getId() {
		return id;
	}
//	public void setId(Long id) {
//		this.id = id;
//	}
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
