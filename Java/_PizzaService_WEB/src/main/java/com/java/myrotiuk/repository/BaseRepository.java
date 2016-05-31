package com.java.myrotiuk.repository;

import java.util.List;

import javax.persistence.EntityManager;

public interface BaseRepository<T> {

	List<T> getAll();
	
	T find(long id);
	
	long insert(T entity);
	
	void delete(long id);
	
	void update(T entity);
	
}
