package ua.rd.pizzaservice.service;

import java.util.List;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.discount.Discountable;

/**
 *
 * @author andrii
 */
public interface DiscountsProvider {

    public List<Discountable> getDiscounts(Order order);
    
}
