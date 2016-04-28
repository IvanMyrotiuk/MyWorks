package ua.rd.pizzaservice.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author andrii
 */
@Converter
public class StateConvertor 
    implements AttributeConverter<State, String>{

    @Override
    public String convertToDatabaseColumn(State attribute) {
        return attribute.state;
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        return new State(dbData);
    }
    
}
