package com.java.myrotiuk.service.discount;

import java.util.List;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.discount.Discount;

public class SimpleDiscountService implements DiscountService {

	private DiscountProvider discountProvider;
	
	public SimpleDiscountService(DiscountProvider discountProvider) {
		this.discountProvider = discountProvider;
	}

	@Override
	public double getDiscount(Order order) {
		double resultDiscount = 0;
		List<Discount> discounts = discountProvider.provideDiscounts(order);
		for (Discount discount : discounts) {
			if (discount.isApplicable()) {
				resultDiscount += discount.countDiscount();
			}
		}
//		if(discounts.size()>0){
//		Discount discount =discounts.get(0);
//		if(discount.isApplicable()) resultDiscount+=discount.countDiscount();
//		}//special for failing test
		return resultDiscount;
	}

}
