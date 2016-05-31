package com.java.myrotiuk.infrustructure;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;


public class BenchMarkProxyBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//System.out.println("After Bean name "+ beanName);
//		System.out.println(Arrays.asList(bean.getClass().getSuperclass().getInterfaces()));
		Class<?> beanClass = bean.getClass();
//		System.out.println("IS PPPPPP"+Enhancer.isEnhanced(beanClass));
//		System.out.println(beanClass.getName());
		Method[] methods = beanClass.getMethods();
		for(Method m: methods){
			//System.out.println(Arrays.asList(m.getAnnotations()));
			if(m.getAnnotation(BenchMark.class) != null){
				bean = MyTimeProxy.getProxyInstance(bean);
			}
		}
		return bean;
	}

}
