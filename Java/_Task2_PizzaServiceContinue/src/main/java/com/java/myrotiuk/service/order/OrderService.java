package com.java.myrotiuk.service.order;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;

public interface OrderService {
	
	Order placeNewOrder(Address address, Integer... pizzasID);
	Order processOrder(long orderId);
	Order completeOrder(long orderId);
	Order cancelOrder(long orderId);
	boolean addPizzaToOrder(long orderId, Integer... pizzasID);
	boolean changeOrderDeletePizza(int orderId, Integer... pizzasID);
	
	long awardCard(Customer customer, String name);

}