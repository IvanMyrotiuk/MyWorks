package com.java.myrotiuk.pizza.infrastructure;

public class ServiceLocator {
	
	private final Config config = new JavaConfig();
	
	private final static ServiceLocator instance =
			new ServiceLocator();//mozem peredat config v constr 
	
	private ServiceLocator(){
		
	}
	
	public static ServiceLocator getInstance(){
		return instance;
	}
	
	public Object lookup(String bean) {
		Class<?> clazz = config.getImpl(bean);
		if(clazz == null){
			throw new RuntimeException("bean not found");
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(); 
		}
	}
}
