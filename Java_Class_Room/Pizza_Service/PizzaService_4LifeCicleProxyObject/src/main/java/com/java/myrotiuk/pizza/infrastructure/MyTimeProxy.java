package com.java.myrotiuk.pizza.infrastructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class MyTimeProxy implements InvocationHandler {

	private Object object;
	
	public MyTimeProxy(Object object) {
		this.object = object;
		System.out.println(object.getClass().getName());
	}
	
	public Object getUnwrappedInvocationHandler(){
		return this.object;
	}
	
	public static Object getProxyInstance(Object object){
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), 
				object.getClass().getInterfaces(), new MyTimeProxy(object));
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method.getName()+" NAAAAAAAAAAAAAAAAAAAAAAAAAMMMMMMMMMMMMMmm");
		System.out.println("SDSFSFSFSFSFSFSF");
		System.out.println(method.getAnnotation(PostConstruct.class));//BanchMark.class));
		System.out.println(method.getAnnotation(BanchMark.class));//BanchMark.class));

		Method m = object.getClass().getMethod(method.getName(), method.getParameterTypes());
		if(m.isAnnotationPresent(BanchMark.class)){
			//if(method.getAnnotation(BanchMark.class) != null){
			BanchMark banchMark = m.getAnnotation(BanchMark.class);
			if(banchMark.active() == true){
				long start = System.nanoTime();
				Object resultOfExecutingMethod = method.invoke(object, args);
				long end = System.nanoTime();
				long result = end - start;
				System.out.println("Method:"+method.getName()+" was working:"+result+" ms");
				return resultOfExecutingMethod;
			}
		}
			return method.invoke(object, args);
	}

}
