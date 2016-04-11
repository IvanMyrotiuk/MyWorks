package ua.rd.pizzaservice;

import ua.rd.pizzaservice.service.OrderService;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.infrustructure.ApplicationContext;
import ua.rd.pizzaservice.infrustructure.JavaConfigApplicationContext;
import ua.rd.pizzaservice.repository.PizzaRepository;

/**
 *
 * @author andrii
 */
public class PizzaApp {
    
    public static void main(String[] args) throws Exception {
        Customer customer = null;        
        Order order;
        
        ApplicationContext ac = new JavaConfigApplicationContext();
        PizzaRepository pizzaRepository = (PizzaRepository) ac.getBean("pizzaRepository");
        System.out.println(pizzaRepository.getPizzaByID(1));
        
        OrderService orderService = (OrderService)
                ac.getBean("orderService");
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        
        System.out.println(order);
    }
}
 