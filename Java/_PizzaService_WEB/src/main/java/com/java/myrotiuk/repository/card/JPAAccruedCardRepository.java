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
		Query query = em.createQuery("select c from AccruedCard c where c.customer = :customer");//:customer//.id = ?1
		query.setParameter("customer", customer);
		//query.setParameter(1, customer.getId());
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
		Customer customer = entity.getCustomer(); 
		if(customer.getId() != 0){
			Customer managedCustomer = em.merge(customer);
			entity.setCustomer(managedCustomer);
		}
		em.persist(entity);
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
