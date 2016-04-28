package ua.rd.pizzaservice.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author andrii
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Customer implements FactoryBean<Customer> {

    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.PERSIST)
    private List<Address> address;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> phones;
    @Version
    private Integer version;

    public List<Address> getAddress() {
        return address;
    }

    public void setstateAddress(List<Address> address) {
        this.address = address;
    }

    
    
    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Customer() {
    }


    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public Customer getObject() throws Exception {
        return new Customer(1, "Abc");
    }

    @Override
    public Class<?> getObjectType() {
        return Customer.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
