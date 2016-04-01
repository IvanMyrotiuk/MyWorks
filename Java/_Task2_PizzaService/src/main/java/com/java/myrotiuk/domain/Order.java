package com.java.myrotiuk.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.java.myrotiuk.domain.discount.Discount;

public class Order {
	private Long id;
	private Customer customer;
	private List<Pizza> pizzas;
	private OrderStatus status;
	private static long currentId = 0l;
	private double orderPriceWithDiscount = -1d;
	private double orderPrice = -1d;
	private boolean changeOrder = true;
	private boolean changeOrderForDiscount = true;
	private List<Discount> discounts = new ArrayList<>();
	
	public enum OrderStatus{
		NEW,IN_PROGRSS, CANCELED, DONE;
	}
	
	public Order(Customer customer, List<Pizza> pizzas) {
		this.customer = customer;
		this.pizzas = pizzas;
		this.status = OrderStatus.NEW;
		this.id = ++currentId;
	}
	
	
	public boolean setOrderStatusToNew(){
		if(this.status == OrderStatus.CANCELED || this.status == OrderStatus.DONE){
			this.status = OrderStatus.NEW;
			return true;
		}
		return false;
	}
	
	public boolean setOrderStatusToProgress(){
		if(this.status == OrderStatus.NEW){
			this.status = OrderStatus.IN_PROGRSS;
			return true;
		}
		return false;
	}
	
	public boolean setOrderStatusToDone(){
		if(this.status == OrderStatus.IN_PROGRSS){
			countOrderPriceWithDiscount();
			customer.getCard().setAmount(orderPriceWithDiscount);
			this.status = OrderStatus.DONE;
			return true;
		}
		return false;
	}
	
	public boolean setOrderStatusToCancel(){
		if(this.status == OrderStatus.NEW || this.status == OrderStatus.IN_PROGRSS){
			this.status = OrderStatus.CANCELED;
			return true;
		}
		return false;
	}
	
	public boolean changeOrderDeletePizza(Integer... pizzasID){
		if(this.getOrderStatus() == OrderStatus.NEW 
				|| this.getOrderStatus() == OrderStatus.CANCELED){
			
			Pizza[] pizzasToSort = (Pizza[]) pizzas.toArray();
			Arrays.sort(pizzasToSort, new Comparator<Pizza>(){
				public int compare(Pizza z1, Pizza z2){
					return z1.getId() - z2.getId();
				}
			});
			
			for(Integer toDelete: pizzasID){
				int del = Arrays.binarySearch(pizzasToSort, toDelete);
				pizzasToSort[del] = null;
			}
			
			List<Pizza> newPizzas = new ArrayList<>();
			for(Pizza pizzaToSet : pizzasToSort){
				newPizzas.add(pizzaToSet);
			}
			pizzas = newPizzas;
			changeOrder = true;
			changeOrderForDiscount = true;
			return true;
		}
		return false;
	}
	
	public boolean addPizzas(List<Pizza> additionalPizzas){
		if(this.pizzas.size() + additionalPizzas.size() <= 10){
			this.pizzas.addAll(additionalPizzas);
			changeOrder = true;
			changeOrderForDiscount = true;
			return true;
		}
		return false;
	}
	
	public OrderStatus getOrderStatus() {
		return status;
	}
	
	public void addDiscount(Discount discount){
		discounts.add(discount);
	}
	
	public double getCountOrderPriceWithDiscount(){
		return orderPriceWithDiscount;
	}
	
	public double countOrderPriceWithDiscount(){
		
		if(changeOrderForDiscount){
			orderPriceWithDiscount = getOrderPrice();
			for(Discount discount: discounts){
				orderPriceWithDiscount -= discount.countDiscount(this);
			}
			changeOrderForDiscount = false;
		}
		return orderPriceWithDiscount;
	}
	
	public double getOrderPrice(){
		return countOrderPrice();
	}
	
	public double countOrderPrice(){

		if(changeOrder){
			for(Pizza pizza : pizzas){
				orderPrice += pizza.getPrice();
			}
			changeOrder = false;
		}
		return orderPrice;
	}
	
	public void setOrderStatus(OrderStatus status) {
		this.status = status;
	}



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
