package ua.rd.pizzaservice;

import java.util.Arrays;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ua.rd.pizzaservice.domain.Address;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.RegistratedCustomer;
import ua.rd.pizzaservice.domain.State;

/**
 *
 * @author andrii
 */
public class JPAWithoutSpringApp {

    public static void main(String[] args) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        Pizza pizza = new Pizza();
        pizza.setName("Margo");
        pizza.setPrice(120.3);
        pizza.setType(Pizza.PizzaType.SEA);

        Address address = new Address();
        address.setCity("Kyiv");
        address.setState(new State("Created"));

        Customer customer = new Customer();
        customer.setName("Andrii");
        customer.setstateAddress(Arrays.asList(address));
        customer.setPhones(Arrays.asList("123", "456"));
        
        address.setCustomer(customer);

        //customer.setPhones(Arrays.asList("123", "456"));
        try {
            em.getTransaction().begin();
            //em.persist(address);
            em.persist(customer);            
            em.getTransaction().commit();
            
            System.out.println(address.getCustomer());
            
            System.out.println(address.getId());
            //em.clear();//global detach
            //em.detach(customer);//make object unmanaged
            
            
            
            //Address p = em.find(Address.class, address.getId());
            //p.setCity("ABC");
            address.setCity("Lviv - 2");
            address = em.merge(address);
            em.refresh(address);//
            //em.joinTransaction();Context Add to the transaction
            em.getTransaction().begin();
            em.getTransaction().commit();
            //em.flush();
            //System.out.println(p.getCustomer());


        } finally {
            em.close();
            emf.close();
        }
    }
}
