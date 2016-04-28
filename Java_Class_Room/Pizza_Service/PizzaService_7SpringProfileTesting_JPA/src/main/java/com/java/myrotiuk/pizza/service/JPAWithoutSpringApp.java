package com.java.myrotiuk.pizza.service;

import java.util.Arrays;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.java.myrotiuk.pizza.domain.Address;
import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.domain.Pizza.Type;
import com.java.myrotiuk.pizza.domain.RegistratedCustomer;
import com.java.myrotiuk.pizza.domain.State;

public class JPAWithoutSpringApp {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = emf.createEntityManager();
		
		Address address = new Address();
		address.setAddress("Beverly Hills 17");
		address.setPhoneNumber("777777777777");
		address.setState(new State("Created"));
		
		Customer customer = new Customer();//new RegCustWithoutDiscrField();//new RegistratedCustomer();//new Customer();
		customer.setName("Ivan");
		//customer.setAddress(address); pre last
		customer.setAddress(Arrays.asList(address, address));
		customer.setPhones(Arrays.asList("123", "456"));
		
		address.setCustomer(customer);//!!!!! we have to set customer to address
		
		Pizza pizza = new Pizza();
		//pizza.setId(2);
		pizza.setName("PizzaJPA");
		pizza.setPrice(117.7);
		pizza.setType(Type.MEAT);
		
		
		
		try{
		
			em.getTransaction().begin();
			//em.persist(pizza);
			//em.persist(address);
			em.persist(customer);
//			em.flush();
			em.getTransaction().commit();

			System.out.println("FFFFF"+address.getCustomer());
			//Pizza p = em.find(Pizza.class, 2); 
			//System.out.println(p);
//	1		Scanner in = new Scanner(System.in);
//	1		in.next();
//	1		em.getTransaction().begin();
//	1		customer.setName("Nik");
//	1		em.getTransaction().commit();

//	1		Customer customer2 = em.find(Customer.class, customer.getId());
//	1		System.out.println(customer2.getAddress().getState());//get(0).
			System.out.println(address.getId());
			em.clear();
			Address p = em.find(Address.class, address.getId());//look at persistence context not look at bd
			System.out.println(p.getCustomer());
		}finally{
			em.close();
			emf.close(); 
		}
		
	}
}
