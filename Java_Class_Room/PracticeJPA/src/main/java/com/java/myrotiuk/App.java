package com.java.myrotiuk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.java.myrotiuk.domain.Card;
import com.java.myrotiuk.domain.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    	EntityManager em  =  emf.createEntityManager();
    	
    	User user = new User();
    	user.setName("Ivan");
    	
    	Card card = new Card();
    	card.setName("VisaGold");
    	card.setUser(user);
    	
    	Card card2 = new Card();
    	card2.setName("Visa");
    	card2.setUser(user);
    	
    	try{
    		
    		em.getTransaction().begin();
    		em.persist(card);
    		em.persist(card2);
    		em.getTransaction().commit();
    		
    	}finally{
    		em.close();
    		emf.close();
    	}
    	
    }
}
