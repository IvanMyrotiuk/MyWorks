package com.java.myrotiuk;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.service.customer.CustomerService;
import com.java.myrotiuk.service.order.OrderService;

public class PizzaApp {

	public static void main(String[] args) {
		
		Order order;
		
		ConfigurableApplicationContext repositoryContext = new ClassPathXmlApplicationContext(new String[]{"repositoryContext.xml"}, false);//"repositoryContext.xml");
		repositoryContext.getEnvironment().setActiveProfiles("prod");
		repositoryContext.refresh();
		ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, false);
		
		appContext.setParent(repositoryContext);
		appContext.refresh();
		
		CustomerService customerService = appContext.getBean(CustomerService.class);
		Address address =customerService.singUp("IvanMyrotiuk", "ivan2@gmail.com", "Beverly Hills 17", "7777-777-77");
		OrderService orderService = appContext.getBean(OrderService.class);
		order = orderService.placeNewOrder(address, 1, 2, 3);
		orderService.addPizzaToOrder(order.getId(), 2,2,2);
		//orderService.cancelOrder(order.getId());
		orderService.changeOrderDeletePizza(order.getId(), 2, 3);
		orderService.processOrder(order.getId());
		order = orderService.completeOrder(order.getId());
		System.out.println(order);
		System.out.println(orderService.getClass().getName());
		appContext.close();
	}

}