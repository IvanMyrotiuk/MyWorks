package com.java.myrotiuk.pizza.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mysql.fabric.xmlrpc.base.Data;

@Entity//(name = "abc")
//@Table(name = "newCustomer")
public class RegistratedCustomer extends Customer {

	//we do not id as we add just new behaviour
	@Temporal( TemporalType.DATE)
	private Date creationDate;//LocalDate
	
	public RegistratedCustomer() {
		creationDate = new Date();//LocalDate.now();
	}

	public Date getDate() {
		return creationDate;
	}

	public void setDate(Date date) {
		this.creationDate = date;
	}
}
