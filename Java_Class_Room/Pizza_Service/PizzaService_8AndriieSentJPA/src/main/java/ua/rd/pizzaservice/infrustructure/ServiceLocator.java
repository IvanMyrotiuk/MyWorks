package ua.rd.pizzaservice.infrustructure;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrii
 */
public class ServiceLocator {
    
    private final Config config = new JavaConfig();
    
    private final static ServiceLocator instance = 
            new ServiceLocator();
    
    private ServiceLocator() {        
    }
    
    public static ServiceLocator getInstance() {
        return instance;
    }
     
    public Object lookup(String bean) {
        Class<?> clazz = config.getImpl(bean);
        if (clazz == null) {
            throw new RuntimeException("Bean not found");
        }
        
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }        
}
