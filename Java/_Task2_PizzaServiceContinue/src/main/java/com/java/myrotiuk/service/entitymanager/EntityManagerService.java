/*package com.java.myrotiuk.service.entitymanager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

@Service//("entityManagerService")
public class EntityManagerService {
	
	private static EntityManagerFactory emf;
	
	@PostConstruct
	private void createEntityManagerFactory(){
		emf = Persistence.createEntityManagerFactory("jpa");
	}
	
	public EntityManager createEntityManager(){
		return emf.createEntityManager();
	}
	
	@PreDestroy
	private void closeEntityManagerFactory(){
		emf.close();
	}

}
*/