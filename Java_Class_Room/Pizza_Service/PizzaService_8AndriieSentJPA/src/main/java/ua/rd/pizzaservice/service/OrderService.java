package ua.rd.pizzaservice.service;

import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Customer;

/**
 *
 * @author andrii
 */
public interface OrderService {

    Order placeNewOrder(Customer customer, Integer... pizzasID);
    
}
