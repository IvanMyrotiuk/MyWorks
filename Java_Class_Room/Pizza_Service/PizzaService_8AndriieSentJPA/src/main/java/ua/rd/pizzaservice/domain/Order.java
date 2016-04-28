package ua.rd.pizzaservice.domain;



import java.util.List;

/**
 *
 * @author andrii
 */
public class Order {
    private static long count;
    
    private Long id;
    private Customer customer;
    private List<Pizza> pizzas;
    private double fullPrice;
    private double discountedPrice;
    
    public Order() {
        id = ++count;        
    }
    
    public Order(Customer customer, List<Pizza> pizzas) {
        this();
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }   
    
    public double getDiscountedPrice() {
        return discountedPrice;
    }    
    
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    
    public double getFullPrice() {
        return pizzas.stream().mapToDouble(Pizza::getPrice).sum();
    }    

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", pizzas=" + pizzas + '}';
    }
    
    
}
