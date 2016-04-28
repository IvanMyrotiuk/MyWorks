package com.java.myrotiuk.pizza.domain;

import org.springframework.beans.factory.FactoryBean;

public class Customer implements FactoryBean<Customer>{
	private int id;
	private String name;
	
	public Customer(){
		
	}
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	@Override
	public Customer getObject() throws Exception {
		return new Customer(2, "QWE");
	}
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Customer.class;
	}
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;//prototype
	}
}
