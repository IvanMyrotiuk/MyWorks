package com.java.myrotiuk.repository.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

@Repository
@Transactional
public class JPAAccruedCardRepository implements AccruedCardRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public AccruedCard getAccruedCardByCustomer(Customer customer) {
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
	public List<AccruedCard> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AccruedCard find(long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long insert(AccruedCard entity) {
		//em.find(Customer.class, entity.getCustomer().getId());
//		Customer customer = entity.getCustomer();
//		em.merge(customer);
//		entity.setCustomer(customer);
		em.persist(entity);
		System.out.println("CAAARDDD"+entity);
		return entity.getId();
	}


	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AccruedCard entity) {
		em.merge(entity);
	}

}
