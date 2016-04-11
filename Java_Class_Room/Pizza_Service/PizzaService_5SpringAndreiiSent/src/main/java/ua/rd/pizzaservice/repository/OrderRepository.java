package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Order;

/**
 *
 * @author andrii
 */
public interface OrderRepository {
    Long saveOrder(Order newOrder);
}
