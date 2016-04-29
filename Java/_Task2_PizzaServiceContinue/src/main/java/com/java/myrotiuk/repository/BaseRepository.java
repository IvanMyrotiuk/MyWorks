package com.java.myrotiuk.repository;

import java.util.List;

import javax.persistence.EntityManager;

public interface BaseRepository<T> {

	List<T> getAll(EntityManager em);
	
	T find(long id, EntityManager em);
	
	long insert(T entity, EntityManager em);
	
	void delete(long id, EntityManager em);
	
	void update(T entity, EntityManager em);
	
}
