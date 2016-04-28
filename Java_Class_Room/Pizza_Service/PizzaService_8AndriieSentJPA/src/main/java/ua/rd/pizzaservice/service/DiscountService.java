package ua.rd.pizzaservice.service;

import ua.rd.pizzaservice.domain.Order;

/**
 *
 * @author andrii
 */
public interface DiscountService {
   
    public double calculateDiscount(Order order);
}
