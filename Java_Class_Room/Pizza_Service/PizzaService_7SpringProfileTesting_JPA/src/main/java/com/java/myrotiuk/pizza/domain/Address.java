package com.java.myrotiuk.pizza.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

//@Embeddable
@Entity
public class Address implements Serializable{
	@Column(name = "ADDR_ID")
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String address;
	private String phoneNumber;
	@Column(name = "ADDR_STATE" )//, columnDefinition = "varchar(10)"
	@Convert(converter = StateConverter.class)
	private State state;
	
	//@OneToOne(mappedBy = "address")
	@ManyToOne(optional = false)
	@JoinTable(name = "CUST_ADDR")
	private Customer customer;
	
	public Address(){
		
	}
	
	public Address(String address, String phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Address [address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}
}
