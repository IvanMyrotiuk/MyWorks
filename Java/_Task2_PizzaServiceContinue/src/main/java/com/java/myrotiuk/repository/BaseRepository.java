package com.java.myrotiuk.repository;

import java.util.List;

public interface BaseRepository<T> {

	List<T> getAll();
	
	T find(long id);
	
	long insert(T entity);
	
	void delete(long id);
	
	void update(T entity);
	
}
