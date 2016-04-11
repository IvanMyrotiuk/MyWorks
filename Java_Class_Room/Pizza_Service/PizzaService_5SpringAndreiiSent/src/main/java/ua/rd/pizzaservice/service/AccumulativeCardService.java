package ua.rd.pizzaservice.service;

import ua.rd.pizzaservice.domain.AccumulativeCard;
import ua.rd.pizzaservice.domain.Customer;

/**
 *
 * @author andrii
 */
public interface AccumulativeCardService {
    AccumulativeCard findCard(Customer customer);
}
