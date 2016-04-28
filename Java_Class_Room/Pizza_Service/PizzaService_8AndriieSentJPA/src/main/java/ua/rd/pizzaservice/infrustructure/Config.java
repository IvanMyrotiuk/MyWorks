package ua.rd.pizzaservice.infrustructure;

/**
 *
 * @author andrii
 */
public interface Config {
    Class<?> getImpl(String bean);
}
