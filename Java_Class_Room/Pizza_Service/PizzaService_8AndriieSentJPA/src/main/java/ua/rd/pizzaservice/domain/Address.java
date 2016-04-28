package ua.rd.pizzaservice.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;

/**
 *
 * @author andrii
 */
@Entity
public class Address implements Serializable {
    @Column(name = "ADDR_ID")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String city;
    @Column(name = "ADDR_SATE")
    @Convert(converter = StateConvertor.class)   
    private State state;
    @ManyToOne(optional = false)
    @JoinTable(name = "CUST_ADDR")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }   

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }    
    
}
