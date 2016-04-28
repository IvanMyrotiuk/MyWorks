package com.java.myrotiuk.pizza.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.springframework.beans.factory.FactoryBean;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)//Singled
@DiscriminatorColumn
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
public class Customer{ //implements FactoryBean<Customer>{
	@Id
	//@TableGenerator(name = "CUST_GEN" , table = "hibernate_sequences")
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUST_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;
	@Embedded
	//@Column(name ="ADR")
	//private Address address;
	//@ElementCollection
	//@OneToOne(cascade = CascadeType.PERSIST)
	//private Address address;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
	private List<Address> address;
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	@ElementCollection(fetch = FetchType.EAGER)// podtag zapros s phone  
	private List<String> phones;
	@Version
	private Integer version;
	
	
	public Customer(){
		
	}
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	
	
//	public Address getAddress() {
//		return address;
//	}

	public List<Address> getAddress() {
		return address;
	}
	
	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}

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
	/*@Override
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
	}*/
}
