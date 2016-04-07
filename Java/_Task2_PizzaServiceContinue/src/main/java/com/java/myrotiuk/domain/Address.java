package com.java.myrotiuk.domain;

public class Address {
	private String address;
	private String phoneNumber;
	
	public Address(String address, String phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
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
