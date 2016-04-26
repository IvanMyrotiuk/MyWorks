package com.java.myrotiuk;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.java.myrotiuk.domain.Card;
import com.java.myrotiuk.domain.Course;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Student;
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
    	
    	Course course1 = new Course();
    	course1.setName("Mathematic");
    	Course course2 = new Course();
    	course2.setName("Java");
    	Course course3 = new Course();
    	course3.setName("Java Professional");
    	
    	Student student1 = new Student();
    	student1.setName("Ivan Myrotiuk");
    	student1.setCourses(Arrays.asList(course1, course2, course3));
    	
    	Student student2 = new Student();
    	student2.setName("Julia Myrotiuk");
    	student2.setCourses(Arrays.asList(course1, course2));
    	
    	Pizza pizza = new Pizza();
    	pizza.setName("BLUE SKY");
    	pizza.setPrice(117.10);
    	
    	Pizza pizza2 = new Pizza();
    	pizza2.setName("GOOD VISION");
    	pizza2.setPrice(107.7);
    	
    	Pizza pizza3 = new Pizza();
    	pizza3.setName("Water fall");
    	pizza3.setPrice(777.7);
    	
    	Order order = new Order();
    	order.setOrder("First Order");
    	//order.addPizza(Arrays.asList(pizza, pizza2, pizza3));
    	
    	Order order2 = new Order();
    	order2.setOrder("Second Order");
    	//order2.addPizza(Arrays.asList(pizza, pizza));
    	
    	try{
    		
    		em.getTransaction().begin();
    		em.persist(card);
    		em.persist(card2);
    		em.persist(student1);
    		em.persist(student2);
    		em.persist(pizza);
    		em.persist(pizza2);
    		em.persist(pizza3);

        	order.addPizza(Arrays.asList(pizza, pizza2, pizza2));

        	order2.addPizza(Arrays.asList(pizza, pizza));
    		em.persist(order);
    		em.persist(order2);
    		em.getTransaction().commit();
    		
    	}finally{
    		em.close();
    		emf.close();
    	}
    	
    }
}
