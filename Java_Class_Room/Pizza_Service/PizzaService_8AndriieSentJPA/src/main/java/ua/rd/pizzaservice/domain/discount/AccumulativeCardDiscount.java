package ua.rd.pizzaservice.domain.discount;

import ua.rd.pizzaservice.domain.AccumulativeCard;
import ua.rd.pizzaservice.domain.discount.Discountable;

/**
 *
 * @author andrii
 */
public class AccumulativeCardDiscount implements Discountable {

    public AccumulativeCardDiscount(AccumulativeCard card) {
    }

    @Override
    public double calculateDiscount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
