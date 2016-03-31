package com.java.myrotiuk.domain;

public class Customer {
	private int id;
	private String name;
	private static int currentId = 1;
	
//	public Customer(int id, String name) {
//		this.id = id;
//		this.name = name;
//	}

	public Customer(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
	
	
}
