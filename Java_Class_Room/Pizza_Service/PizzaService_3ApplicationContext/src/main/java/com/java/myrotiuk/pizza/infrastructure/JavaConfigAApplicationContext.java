package com.java.myrotiuk.pizza.infrastructure;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class JavaConfigAApplicationContext implements ApplicationContext {
	private final Config config = new JavaConfig();
	private final Map<String, Object> context = new HashMap<>();
	@Override
	public Object getBean(String beanName) throws Exception{
		//v service est dependencies ne mozem newInstance call
		if(context.containsKey(beanName)){
			return context.get(beanName);
		}
		
		Class<?> clazz = config.getImpl(beanName);
		if(clazz == null){
			throw new RuntimeException("beanName not found"+beanName);
		}
		
		Constructor<?> constructor = clazz.getConstructors()[0];
		Class<?>[]  paramTypes = constructor.getParameterTypes();
		Object bean = null;
		if(paramTypes.length == 0){
			bean = clazz.newInstance();
		}else{
			Object[] paramBeans = new Object[paramTypes.length];
			for(int i = 0; i < paramTypes.length; i++){
				String paramTypeName = paramTypes[i].getSimpleName();
				System.out.println(paramTypeName);
				String paramName = Character.toLowerCase(paramTypeName.charAt(0)) + paramTypeName.substring(1);
				paramBeans[i] = getBean(paramName);//to lower case 
			}
			bean = constructor.newInstance(paramBeans);
		}
		
		
		context.put(beanName, bean);
		return bean;
	}

}
