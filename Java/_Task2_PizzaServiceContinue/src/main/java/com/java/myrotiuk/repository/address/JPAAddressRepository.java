package com.java.myrotiuk.repository.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;

@Repository
@Transactional
public class JPAAddressRepository implements AddressRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Address> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address find(long id) {
		return em.find(Address.class, id);
	}

	@Override
	public long insert(Address entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Address entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Address> getAddressesByCustomer(Customer customer) {
		Query query = em.createQuery("select a from Address a where a.customer.id = ?1");
		query.setParameter(1, customer.getId());
		List<Address> addresses = query.getResultList();
		return addresses;
	}

}
