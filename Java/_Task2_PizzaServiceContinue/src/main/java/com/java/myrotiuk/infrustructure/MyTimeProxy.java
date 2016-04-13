package com.java.myrotiuk.infrustructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;


public class MyTimeProxy implements InvocationHandler {

	private Object object;
	
	public MyTimeProxy(Object object) {
		this.object = object;
	}
	
	public Object getUnwrappedInvocationHandler(){
		return this.object;
	}
	
	public static Object getProxyInstance(Object object){
		Class<?> objectClass = object.getClass();
		if(Enhancer.isEnhanced(objectClass)){
			return Proxy.newProxyInstance(object.getClass().getClassLoader(), 
					objectClass.getSuperclass().getInterfaces(), new MyTimeProxy(object));
		}else{
			return Proxy.newProxyInstance(object.getClass().getClassLoader(), 
					objectClass.getInterfaces(), new MyTimeProxy(object));
		}
		
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Method m = object.getClass().getMethod(method.getName(), method.getParameterTypes());
		if(m.isAnnotationPresent(BenchMark.class)){
			BenchMark banchMark = m.getAnnotation(BenchMark.class);
			if(banchMark.active() == true){
				long start = System.nanoTime();
				Object resultOfExecutingMethod = method.invoke(object, args);
				long end = System.nanoTime();
				long result = end - start;
				System.out.println("Method:"+method.getName()+" was working:"+result+" nano sec");
				return resultOfExecutingMethod;
			}
		}
			return method.invoke(object, args);
	}

}
