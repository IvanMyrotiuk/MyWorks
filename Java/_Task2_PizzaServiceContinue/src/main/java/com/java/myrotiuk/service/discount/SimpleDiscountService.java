package com.java.myrotiuk.service.discount;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.domain.discount.Discount;

@Service("discountService")
public class SimpleDiscountService implements DiscountService {

	private DiscountProvider discountProvider;
	
	@Autowired
	public SimpleDiscountService(DiscountProvider discountProvider) {
		this.discountProvider = discountProvider;
	}

	@Override
	public double getDiscount(Order order, EntityManager em) {
		double resultDiscount = 0;
		List<Discount> discounts = discountProvider.provideDiscounts(order, em);
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
