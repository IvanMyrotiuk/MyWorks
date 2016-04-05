package com.java.myrotiuk.pizza.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.java.myrotiuk.pizza.repository.PizzaRepository;
import com.java.myrotiuk.pizza.repository.inmempizza.InMemPizzaRepository;

public class BeanBuilder {

	private final Class<?> clazz;
	private ApplicationContext applicationContext;
	private Object bean;
	private Object proxy;

	public BeanBuilder(Class<?> clazz, ApplicationContext applicationContext) {
		this.clazz = clazz;
		this.applicationContext = applicationContext;
	}

	protected Object createBean()
			throws InstantiationException, IllegalAccessException, Exception, InvocationTargetException {
		Constructor<?> constructor = clazz.getConstructors()[0];

		if (constructor.getParameterCount() == 0) {
			bean = clazz.newInstance();
		} else {
			bean = createNewInstanceWithParams(constructor);
		}
		return bean;
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
			getParams(paramTypes, paramBeans, i);
		}
		return paramBeans;
	}

	private void getParams(Class<?>[] paramTypes, Object[] paramBeans, int i) throws Exception {
		String paramName = getBeanNameByType(paramTypes[i]);
		paramBeans[i] = applicationContext.getBean(paramName);// to lower//cicle
																// case
	}

	private String getBeanNameByType(Class<?> paramTypes) {
		String paramTypeName = paramTypes.getSimpleName();
		System.out.println(paramTypeName);
		String paramName = changeFirstLetterToLowerCase(paramTypeName);
		return paramName;
	}

	private String changeFirstLetterToLowerCase(String paramTypeName) {
		String paramName = Character.toLowerCase(paramTypeName.charAt(0)) + paramTypeName.substring(1);
		return paramName;
	}

	public void createBeanProxy() {
		Method[] methods = clazz.getMethods();
		for(Method m: methods){
			if(m.getAnnotation(BanchMark.class) != null){
				proxy = MyTimeProxy.getProxyInstance(bean);
				break;
			}
		}
	}
	
	public void callPostConstructMethod() throws Exception {
		Method[] methods = clazz.getMethods();
		for(Method m : methods){
			if(m.getAnnotation(PostConstruct.class) != null){
				m.invoke(bean);
			}
		}
	}

	public void callInitMethod() throws Exception{
		Method[] methods = clazz.getMethods();
		for(Method m: methods){
			if(m.getName().equals("init")){
//				InvocationHandler ih = Proxy.getInvocationHandler(bean);
//				Object original = ((MyTimeProxy)ih).getUnwrappedInvocationHandler();
				m.invoke(bean);
			}
		}
	}

	public Object build() {
		if(proxy != null){
			return proxy;
		}else{
			return bean;
		}
	}
}