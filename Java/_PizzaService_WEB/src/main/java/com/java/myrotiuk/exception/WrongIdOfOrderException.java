package com.java.myrotiuk.exception;

public class WrongIdOfOrderException extends IllegalArgumentException {

	public WrongIdOfOrderException(String name) {
		super(name);
	}
}
