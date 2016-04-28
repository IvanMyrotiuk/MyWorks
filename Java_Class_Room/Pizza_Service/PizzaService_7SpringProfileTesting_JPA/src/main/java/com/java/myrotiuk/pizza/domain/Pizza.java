package com.java.myrotiuk.pizza.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pizza {
	@Id
	//@GeneratedValue(strategy = GenerationType.TABLE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String name;
	private double price;
	@Enumerated(EnumType.STRING)
	//@Basic(fetch = FetchType.LAZY)
	@Column( name = "pizza")//"pizza_type"//unique = true,
	private Type type;
	
	
	public enum Type{VEGETERIAN, SEA, MEAT}

	
	public Pizza(){
		
	}

	public Pizza(Integer id, String name, double price, Type type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
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
