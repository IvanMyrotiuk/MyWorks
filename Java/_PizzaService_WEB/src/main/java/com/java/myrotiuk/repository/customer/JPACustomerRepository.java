package com.java.myrotiuk.repository.customer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Customer;

@Repository
@Transactional
public class JPACustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(Customer entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Customer entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer findCustomerByEmail(String email) {
		Query query = em.createQuery("select c from Customer c where c.email = ?1");
		query.setParameter(1, email);
		List<Customer> customer = query.getResultList();
		if(customer.isEmpty()){
			return null;
		}else{
			return customer.get(0);
		}
	}

}
