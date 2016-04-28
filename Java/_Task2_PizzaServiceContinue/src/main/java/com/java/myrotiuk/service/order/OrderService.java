package com.java.myrotiuk.service.order;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Order;

public interface OrderService {
	
	Order placeNewOrder(Address address, Integer... pizzasID);

}