package com.java.myrotiuk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//@Size(min = 4, max = 40, message = "Username must be between 4 and 40 characters long.")
	private String name;
	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}", message = "Invalide email address")
	@NotNull
	private String email;
	//@Size(min = 6, message = "password must be at least 6 charecter long")
//	@NotNull
//	private int password;
	
	public Customer() {
	}
	
	public Customer(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

/*	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}*/

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" +  email+"]";
	}
	
}
