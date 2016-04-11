package ua.rd.pizzaservice.infrustructure;

import ua.rd.pizzaservice.repository.PizzaRepository;

/**
 *
 * @author andrii
 */
public interface ApplicationContext {

    public Object getBean(String bean) throws Exception ;
    
}
