package com.java.myrotiuk;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.myrotiuk.domain.Address;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.domain.Order;
import com.java.myrotiuk.service.order.OrderService;

public class PizzaApp {

	public static void main(String[] args) {

		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		
		Address address = new Address(new Customer("John"),"Beverly Hills", "7777-777-77");
		//Customer customer = new Customer("John", new Address("Beverly Hills", "7777-777-77"));
		Order order;
		
		ConfigurableApplicationContext repositoryContext = new ClassPathXmlApplicationContext("repositoryContext.xml");
		
		ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, false);
		
		appContext.setParent(repositoryContext);
		appContext.refresh();
		
		/*PizzaRepository repo = appContext.getBean("springJDBCPizzaRepository", SpringJDBCPizzaRepository.class);
		Pizza myPizza = repo.find(3);
		System.out.println("myPiza from DB"+myPizza);
		//System.out.println(repo.insert(new Pizza("Strong beef2",111.5,Type.MEAT)));
		Pizza pizzaTOUpdate = repo.find(3);
		pizzaTOUpdate.setName("Blue SKY");
		repo.update(pizzaTOUpdate);
		//repo.delete(2);
		System.out.println("pizza by id=>"+repo.find(3));
		System.out.println("listOfPizzas=>"+ repo.getAll());*/
		
		
		/*OrderService orderService = (OrderService) appContext.getBean(OrderService.class);//"simpleOrderService");//= new SimpleOrderService();
		order = orderService.placeNewOrder(address, 1, 2, 3);
		//SimpleOrderService simpleOrder = (SimpleOrderService)orderService;
		orderService.addPizzaToOrder(order.getId(), 2,2,2);
		//simpleOrder.changeOrderDeletePizza(order.getId(), 3);
		orderService.processOrder(order.getId());
		order = orderService.completeOrder(order.getId());
		System.out.println(order);
		System.out.println(orderService.getClass().getName());
		*/
		
		OrderService orderService = appContext.getBean(OrderService.class);
		order = orderService.placeNewOrder(address, 1, 2, 3);
		orderService.addPizzaToOrder(order.getId(), 2,2,2);
		orderService.processOrder(order.getId());
		order = orderService.completeOrder(order.getId());
		System.out.println(order);
		System.out.println(orderService.getClass().getName());
		
		appContext.close();
		
	}

}