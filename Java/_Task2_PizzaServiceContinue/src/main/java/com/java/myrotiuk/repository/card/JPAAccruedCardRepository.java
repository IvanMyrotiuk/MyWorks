package com.java.myrotiuk.repository.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.service.entitymanager.EntityManagerService;

@Repository
public class JPAAccruedCardRepository implements AccruedCardRepository {

/*	@Autowired
	private EntityManagerService ems;
	
	@Override
	public List<AccruedCard> getAll() {
		EntityManager em = ems.createEntityManager();
		em.getTransaction().begin();
		return null;
	}*/


	@Override
	public AccruedCard getAccruedCardByCustomer(Customer customer, EntityManager em) {
		//AccruedCard accruedCard = em.
		Query query = em.createQuery("select c from AccruedCard c where c.customer = :customer");
		query.setParameter("customer", customer);
		List<AccruedCard> accCard = query.getResultList();
		if(accCard.isEmpty()){
			return null;
		}else{
			return accCard.get(0);
		}
	}


	@Override
	public List<AccruedCard> getAll(EntityManager em) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AccruedCard find(long id, EntityManager em) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long insert(AccruedCard entity, EntityManager em) {
		em.persist(entity);
		return entity.getId();
	}


	@Override
	public void delete(long id, EntityManager em) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(AccruedCard entity, EntityManager em) {
		em.merge(entity);
	}

}
