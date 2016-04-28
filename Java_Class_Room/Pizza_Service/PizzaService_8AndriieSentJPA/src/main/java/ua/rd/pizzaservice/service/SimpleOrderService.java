package ua.rd.pizzaservice.service;

import ua.rd.pizzaservice.infrustructure.ServiceLocator;
import ua.rd.pizzaservice.repository.InMemOrderRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.repository.InMemPizzaRepository;
import ua.rd.pizzaservice.repository.OrderRepository;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ua.rd.pizzaservice.infrustructure.Benchmark;

/**
 *
 * @author andrii
 */

public class SimpleOrderService implements OrderService { 
       // ApplicationContextAware {

    // private ServiceLocator locator = ServiceLocator.getInstance();
    private OrderRepository orderRepository;
    //        = (OrderRepository) locator.lookup("orderRepository");
    private PizzaRepository pizzaRepository;
    //        = (PizzaRepository) locator.lookup("pizzaRepository");
    //private ApplicationContext appContext;
    private Customer customer;

    public SimpleOrderService(OrderRepository orderRepository, PizzaRepository pizzaRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @Required
    public void setCustomer(Customer customer) {
        this.customer = customer;
        System.out.println(customer);
    }    

//    public void setApplicationContext(ApplicationContext appContext) {
//        this.appContext = appContext;
//    }

    @Override
    @Benchmark
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
        List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
        Order newOrder = createOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzas(pizzas);

        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    protected Order createOrder(){
        return null;
    }

    private List<Pizza> pizzasByArrOfId(Integer[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Integer id : pizzasID) {
            pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }

}
