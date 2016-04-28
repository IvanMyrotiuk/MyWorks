package com.java.myrotiuk.pizza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.java.myrotiuk.pizza.domain.Customer;
import com.java.myrotiuk.pizza.domain.Order;
import com.java.myrotiuk.pizza.domain.Pizza;
import com.java.myrotiuk.pizza.infrastructure.ServiceLocator;
//import com.java.myrotiuk.pizza.domain.Pizza.Type;
import com.java.myrotiuk.pizza.repository.OrderRepository;
import com.java.myrotiuk.pizza.repository.PizzaRepository;
import com.java.myrotiuk.pizza.repository.inmemorder.InMemOrderRepository;
import com.java.myrotiuk.pizza.repository.inmempizza.InMemPizzaRepository;

public abstract class SimpleOrderService implements OrderService{//, ApplicationContextAware {
	
	private ServiceLocator locator = ServiceLocator.getInstance();
	
	private OrderRepository orderRepository; //= 
	//		(OrderRepository) locator.lookup("orderRepository");//new InMemOrderRepository();
	private PizzaRepository pizzaRepository; //= 
	//		(PizzaRepository) locator.lookup("pizzaRepository");//new InMemPizzaRepository();
	
	//private Order order;
	
	//private ApplicationContext appContext;
	
	private Customer customer;
	
	 public SimpleOrderService(OrderRepository orderRepository, PizzaRepository pizzaRepository) {
		this.orderRepository = orderRepository;
		this.pizzaRepository = pizzaRepository;
	}

	 

	 @Required
	public void setCustomer(Customer customer) {
		this.customer = customer;
		System.out.println("CUSTOMER" +customer);
	}



	/* (non-Javadoc)
	 * @see com.java.myrotiuk.pizzaservice.OrderService#placeNewOrder(com.java.myrotiuk.pizzaservice.Customer, java.lang.Integer)
	 */
	public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
         List<Pizza> pizzas = pizzaByArrOfId(pizzasID);
         Order newOrder = createOrder();
         newOrder.setCustomer(customer);
         newOrder.setPizzas(pizzas);
         
         orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
         return newOrder;
	 }


	protected abstract Order createOrder();// {//will be implemented by container will call getBean withOrder
		//Order newOrder = new Order(customer, pizzas);
//	2	Order order = (Order)appContext.getBean("order");
//	2	return order;
	//}
	
//	protected Order createOrder(){
//		return null;
//	}

	private List<Pizza> pizzaByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();
         
         for(Integer id : pizzasID){
                 pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
         }
		return pizzas;
	}

}
