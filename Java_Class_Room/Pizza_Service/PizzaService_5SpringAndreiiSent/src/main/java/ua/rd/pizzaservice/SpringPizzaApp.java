package ua.rd.pizzaservice;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.service.OrderService;

/**
 *
 * @author andrii
 */
public class SpringPizzaApp {

    public static void main(String[] args) {

        ConfigurableApplicationContext repositoryContext
                = new ClassPathXmlApplicationContext(
                        "repositoryContext.xml"
                );

        ConfigurableApplicationContext appContext
                = new ClassPathXmlApplicationContext(
                        new String[]{"appContext.xml"}, false
                );
        appContext.setParent(repositoryContext);        
        appContext.refresh();
        
        PizzaRepository pr
                = (PizzaRepository) appContext.getBean("pizzaRepository");
        System.out.println(pr.getPizzaByID(1));

        OrderService orderService = (OrderService) appContext.getBean("orderService");
        Order order = orderService.placeNewOrder(null, 1);

        System.out.println(order);

        Customer customer = appContext.getBean(Customer.class);
        System.out.println(customer);

        ApplicationContext parent = appContext.getParent();
        System.out.println("Parent: " + parent);

        appContext.close();
        repositoryContext.close();

    }
}
