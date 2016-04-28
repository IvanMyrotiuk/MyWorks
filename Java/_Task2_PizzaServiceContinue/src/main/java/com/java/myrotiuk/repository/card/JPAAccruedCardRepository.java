package com.java.myrotiuk.repository.card;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.entitymanager.EntityManagerService;

public class JPAAccruedCardRepository implements AccruedCardRepository {

	@Autowired
	private EntityManagerService ems;
	
	@Override
	public List<AccruedCard> getAll() {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		return null;
	}

	@Override
	public AccruedCard find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(AccruedCard entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AccruedCard entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccruedCard getAccruedCardByCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
