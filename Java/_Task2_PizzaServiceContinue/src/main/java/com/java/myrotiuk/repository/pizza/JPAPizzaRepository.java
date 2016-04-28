package com.java.myrotiuk.repository.pizza;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;
import com.java.myrotiuk.service.entitymanager.EntityManagerService;

@Repository
//@DependsOn("entityManagerService")
public class JPAPizzaRepository implements PizzaRepository{

	//private List<Pizza> pizzas;
	@Autowired
	private EntityManagerService ems;
	
	@PostConstruct
	public void initPizzas(){
		
		Pizza pizza1= new Pizza("Good day", 101.50, Type.SEA);
		Pizza pizza2 = new Pizza("Blue sky", 111.50, Type.MEAT);
		Pizza pizza3 = new Pizza("Woterfall", 131.50, Type.VEGETERIAN);
		EntityManager em = ems.createEntityManager(); 
		em.getTransaction().begin();
		em.persist(pizza1);
		em.persist(pizza2);
		em.persist(pizza3);
		em.getTransaction().commit();
		em.close();
		
	}
	
	//@Resource(name = "pizzasList")
	public void setPizzas(List<Pizza> pizzas){
		EntityManager em = ems.createEntityManager(); 
		for(Pizza p : pizzas){
			em.persist(p);
		}
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Pizza> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pizza find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(Pizza entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Pizza entity) {
		// TODO Auto-generated method stub
		
	}

}
