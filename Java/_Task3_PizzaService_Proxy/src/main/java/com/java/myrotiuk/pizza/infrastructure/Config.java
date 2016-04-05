package com.java.myrotiuk.pizza.infrastructure;

public interface Config {
	Class<?> getImpl(String bean);//bean name of impl class//can parse xml name dla podmen xml to name
}
