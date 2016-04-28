package ua.rd.pizzaservice.service;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;

/**
 *
 * @author andrii
 */
@ContextConfiguration(locations = {
    "classpath:/appContext.xml"},
        inheritInitializers = true)
public class SimpleOrderServiceTest extends RepositoryTestConifg {

    @Autowired
    private OrderService orderService;

    @Test
    public void testPlaceNewOrder() {
        System.out.println("placeNewOrder");
        Customer customer = null;
        Integer[] pizzasID = new Integer[]{1};
        Order result = orderService.placeNewOrder(customer, pizzasID);
        System.out.println(result);
    }

}
