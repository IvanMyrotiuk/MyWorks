package com.java.myrotiuk.domain;

public class AccruedCard {
	private int id;
	private double amount;
	private static int currentId = 1;
	private Customer customer;
	
	public AccruedCard(Customer customer) {
		this.customer = customer;
		this.id = currentId++;
	}

	public int getId() {
		return id;
	}

//	public void setId(int id) {
//		this.id = id;
//	}
	
	

	public double getAmount() {
		return amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
