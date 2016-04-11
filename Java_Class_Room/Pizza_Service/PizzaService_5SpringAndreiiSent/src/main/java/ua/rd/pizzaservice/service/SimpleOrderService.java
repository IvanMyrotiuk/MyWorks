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

/**
 *
 * @author andrii
 */
public class SimpleOrderService implements OrderService {

    // private ServiceLocator locator = ServiceLocator.getInstance();

    private OrderRepository orderRepository;
    //        = (OrderRepository) locator.lookup("orderRepository");
    private PizzaRepository pizzaRepository;
    //        = (PizzaRepository) locator.lookup("pizzaRepository");

    public SimpleOrderService(OrderRepository orderRepository, PizzaRepository pizzaRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
        List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
        Order newOrder = createOrder(customer, pizzas);

        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    private Order createOrder(Customer customer, List<Pizza> pizzas) {
        Order newOrder = new Order(customer, pizzas);
        return newOrder;
    }

    private List<Pizza> pizzasByArrOfId(Integer[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Integer id : pizzasID) {
            pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }

}
