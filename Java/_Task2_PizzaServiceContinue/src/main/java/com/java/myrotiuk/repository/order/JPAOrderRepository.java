package com.java.myrotiuk.repository.order;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.Order;

@Repository
public class JPAOrderRepository implements OrderRepository{

	@Override
	public Optional<Order> getOrder(long orderId, EntityManager em) {
		
		return Optional.of(em.find(Order.class, new Long(orderId)));
	}

	@Override
	public List<Order> getAll(EntityManager em) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order find(long id, EntityManager em) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insert(Order entity, EntityManager em) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public void delete(long id, EntityManager em) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order entity, EntityManager em) {
		em.merge(entity);
	}

}
