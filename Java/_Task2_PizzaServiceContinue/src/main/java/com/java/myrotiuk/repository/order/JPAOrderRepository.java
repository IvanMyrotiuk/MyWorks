package com.java.myrotiuk.repository.order;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;

@Repository
@Transactional
public class JPAOrderRepository implements OrderRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Order> getOrder(long orderId) {
		System.out.println("Order id To get!!!!!!!!!!"+orderId);
		return Optional.of(em.find(Order.class, new Long(orderId)));
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(Order entity) {
		Address address = entity.getAddress();
		if(address.getId() != 0){
			Address managedAddress = em.merge(address);
			entity.setAddress(managedAddress);
		}
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order entity) {
		em.merge(entity);
	}

}
