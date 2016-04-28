package com.java.myrotiuk.pizza.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class StateConverter implements AttributeConverter<State, String>{

	@Override
	public String convertToDatabaseColumn(State attribute) {
		return attribute.state;
	}

	@Override
	public State convertToEntityAttribute(String dbData) {
		return new State(dbData);
	}

}
