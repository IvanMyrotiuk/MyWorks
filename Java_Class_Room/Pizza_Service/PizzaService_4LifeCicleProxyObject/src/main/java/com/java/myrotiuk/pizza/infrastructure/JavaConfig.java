package com.java.myrotiuk.pizza.infrastructure;

import java.util.HashMap;
import java.util.Map;

import com.java.myrotiuk.pizza.repository.inmemorder.InMemOrderRepository;
import com.java.myrotiuk.pizza.repository.inmempizza.InMemPizzaRepository;
import com.java.myrotiuk.pizza.service.SimpleOrderService;

public class JavaConfig implements Config {

	private Map<String, Class<?>> beans = new HashMap<>();
	
	{
		beans.put("orderRepository", InMemOrderRepository.class);
		beans.put("pizzaRepository", InMemPizzaRepository.class);
		beans.put("orderService", SimpleOrderService.class);
	}
	
	@Override
	public Class<?> getImpl(String bean) {
		return beans.get(bean);
	}

}
