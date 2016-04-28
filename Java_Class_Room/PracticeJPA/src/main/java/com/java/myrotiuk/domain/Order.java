package com.java.myrotiuk.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MapKeyJoinColumns;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "Orderr")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String orderr;
	
	
	@ElementCollection
	@CollectionTable(name = "Order_Pizzas", joinColumns = {@JoinColumn(name = "ORDERR_IDdddd")})
	//@JoinTable(name = "JoinTableOrderPizzas", joinColumns = {@JoinColumn(name = "Order_idddd")})
	@MapKeyJoinColumn(name = "PIZZASSSS_ID")
	@Column(name = "Quantityy")
	//@Cascade(value={CascadeType.ALL})
	private Map<Pizza, Integer> pizzas = new HashMap<>();
	
	public void addPizza(List<Pizza> pizza){
		for(Pizza p : pizza){
			if(pizzas.containsKey(p)){
				int count = pizzas.get(p);
				pizzas.put(p, count + 1);
			}else{
				pizzas.put(p, 1);
			}
//			Integer count = pizzas.get(p);
//			if(count == null){
//				pizzas.put(p, 1);
//			}else{
//				pizzas.put(p, count + 1);
//			}
		}
	}

	public String getOrder() {
		return orderr;
	}

	public void setOrder(String order) {
		this.orderr = order;
	}
}
