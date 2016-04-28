package ua.rd.pizzaservice.service;

import java.util.ArrayList;
import java.util.List;
import ua.rd.pizzaservice.domain.discount.AccumulativeCardDiscount;
import ua.rd.pizzaservice.domain.AccumulativeCard;
import ua.rd.pizzaservice.domain.discount.PizzaDiscount;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.discount.Discountable;

/**
 *
 * @author andrii
 */
public class SimpleDiscountService implements DiscountService {
    private AccumulativeCardService accumulativeCardService;
    private DiscountsProvider discountsProvider;
    
    @Override
    public double calculateDiscount(Order order) {
        List<Discountable> discounts = getDiscounts(order);
//        List<Discountable> discounts = discountsProvider.getDiscounts(order);
        double discount = applyDiscounts(discounts);                        
        return discount;
    }

    private List<Discountable> getDiscounts(Order order) {
        List<Discountable> discounts = new ArrayList<>();
        Discountable pizzaDiscount = pizzasDiscount(order);
        Discountable accCardDiscount = accumulativeCardDiscount(order);
        discounts.add(pizzaDiscount);
        discounts.add(accCardDiscount);
        return discounts;
    }

    private Discountable accumulativeCardDiscount(Order order) {
        AccumulativeCard card = accumulativeCardService.findCard(order.getCustomer());
        Discountable accCardDiscount = new AccumulativeCardDiscount(card);
        return accCardDiscount;
    }

    private Discountable pizzasDiscount(Order order) {
        Discountable pizzaDiscount = new PizzaDiscount(order.getPizzas());
        return pizzaDiscount;
    }

    private double applyDiscounts(List<Discountable> discounts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
