package com.java.myrotiuk.domain;

public class Pizza {

	private int id;
	private String name;
	private double price;
	private Type type;
	private static int currentId = 1;

	public enum Type {
		VEGETERIAN, SEA, MEAT
	}

	 public Pizza(int id, String name, double price, Type type) {
	 this.id = id;
	 this.name = name;
	 this.price = price;
	 this.type = type;
	 currentId = id;
	 currentId++;
	 }

	public Pizza(String name, double price, Type type) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.id = currentId++;
	}

	public int getId() {
		return id;
	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Pizza [id=" + id + ", name=" + name + ", price=" + price + ", type=" + type + "]";
	}

}
