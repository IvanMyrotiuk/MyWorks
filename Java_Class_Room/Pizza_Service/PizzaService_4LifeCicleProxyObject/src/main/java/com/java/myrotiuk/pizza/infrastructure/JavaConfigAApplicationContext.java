package com.java.myrotiuk.pizza.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class JavaConfigAApplicationContext implements ApplicationContext {
	private final Config config = new JavaConfig();
	private final Map<String, Object> context = new HashMap<>();

	@Override
	public Object getBean(String beanName) throws Exception {
		// v service est dependencies ne mozem newInstance call
		if (context.containsKey(beanName)) {
			return context.get(beanName);
		}

		Class<?> clazz = config.getImpl(beanName);
		
		if (clazz == null) {
			throw new RuntimeException("beanName not found" + beanName);
		}

		// Object bean = createBean(clazz);

		BeanBuilder builder = new BeanBuilder(clazz, this);
		builder.createBean();
		builder.createBeanProxy();
		//builder.callPostConstructMethod();
		builder.callInitMethod();
		Object bean = builder.build();

		context.put(beanName, bean);
		return bean;
	}
	
	/*class BeanBuilder {

		private final Class<?> clazz;
		//private ApplicationContext applicationContext;
		private Object bean;

		public BeanBuilder(Class<?> clazz/*, ApplicationContext applicationContext*///) {
			//this.clazz = clazz;
			//this.applicationContext = applicationContext;
		//}
/*
		private void createBean()
				throws InstantiationException, IllegalAccessException, Exception, InvocationTargetException {
			Constructor<?> constructor = clazz.getConstructors()[0];

			//Object bean = null;
			if (constructor.getParameterCount() == 0) {
				bean = clazz.newInstance();
			} else {
				bean = createNewInstanceWithParams(constructor);
			}
			//return bean;
		}

		private Object createNewInstanceWithParams(Constructor<?> constructor)
				throws Exception, InstantiationException, IllegalAccessException, InvocationTargetException {
			Object bean;
			Object[] paramBeans = getParams(constructor);
			bean = constructor.newInstance(paramBeans);
			return bean;
		}

		private Object[] getParams(Constructor<?> constructor) throws Exception {
			Class<?>[] paramTypes = constructor.getParameterTypes();
			Object[] paramBeans = new Object[paramTypes.length];
			for (int i = 0; i < paramTypes.length; i++) {
				paramBeans[i] = getBeanByType(paramTypes[i]);
			}
			return paramBeans;
		}

		private Object getBeanByType(Class<?> paramTypes) throws Exception {
			String paramName = getBeanNameByType(paramTypes);
			return /*applicationContext.*///getBean(paramName);// to lower
																	// case
		//}

		/*private String getBeanNameByType(Class<?> paramTypes) {
			String paramTypeName = paramTypes.getSimpleName();
			System.out.println(paramTypeName);
			String paramName = changeFirstLetterToLowerCase(paramTypeName);
			return paramName;
		}

		private String changeFirstLetterToLowerCase(String paramTypeName) {
			String paramName = Character.toLowerCase(paramTypeName.charAt(0)) + paramTypeName.substring(1);
			return paramName;
		}

//		public void createBean() {
//
//		}

		public void createBeanProxy() {
			//instean of bean return proxy;
		}
		
		public void callPostConstructMethod() throws Exception {
			Method[] methods = clazz.getMethods();
			for(Method m : methods){
				if(m.getAnnotation(PostConstruct.class) != null){
					m.invoke(bean);
				}
			}
		}

		public void callInitMethod() throws Exception {
			Method [] methods = clazz.getMethods();
			for(Method m: methods){
				if(m.getName().equals("init")){
					m.invoke(bean);
				}
			}
		}

		public Object build() {
			return bean;
		}
	}*/
}
