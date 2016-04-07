package com.java.myrotiuk.domain;

import java.util.Optional;

public class Customer {
	private int id;
	private String name;
	private Address address;
	private static int currentId = 1;
	
	public Customer(String name, Address address) {
		this.address = address;
		this.name = name;
		this.id = currentId++;
		//Optional.of//(address);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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