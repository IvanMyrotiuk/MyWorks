package com.java.myrotiuk.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "customer_id")
	private Customer customer;
	private String address;
	private String phoneNumber;
	
	public Address() {
	}
	
	public Address(Customer customer, String address, String phoneNumber) {
		this.customer = customer;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
		return id;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		return "Address [id=" + id + ", customer=" + customer + ", address=" + address + ", phoneNumber=" + phoneNumber
				+ "]";
	}
}
