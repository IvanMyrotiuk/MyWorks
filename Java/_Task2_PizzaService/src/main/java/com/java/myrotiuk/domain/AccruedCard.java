package com.java.myrotiuk.domain;

public class AccruedCard {
	private int id;
	private double amount;
	private static int currentId = 1;
	
	public AccruedCard(double amount) {
		this.amount = amount;
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

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
