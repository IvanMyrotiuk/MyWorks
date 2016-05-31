package com.java.myrotiuk.repository.pizza;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

@Repository
@Transactional
public class JPAPizzaRepository implements PizzaRepository{

	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	public void initPizzas(){
		
//		Pizza pizza1= new Pizza("Good day", 101.50, Type.SEA);
//		Pizza pizza2 = new Pizza("Blue sky", 111.50, Type.MEAT);
//		Pizza pizza3 = new Pizza("Woterfall", 131.50, Type.VEGETERIAN);
//		em.persist(pizza1);
//		em.persist(pizza2);
//		em.persist(pizza3);
	}
	
	public void setPizzas(List<Pizza> pizzas){ 
		for(Pizza p : pizzas){
			em.persist(p);
		}
	}
	
	@Override
	public List<Pizza> getAll() {
		TypedQuery<Pizza> query = em.createQuery("select p from Pizza p", Pizza.class);
		return query.getResultList();
	}

	@Override
	public Pizza find(long id) {
		return em.find(Pizza.class, new Long(id));
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
