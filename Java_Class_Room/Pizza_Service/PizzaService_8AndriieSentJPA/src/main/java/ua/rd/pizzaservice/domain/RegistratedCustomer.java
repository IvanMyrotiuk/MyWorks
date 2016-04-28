package ua.rd.pizzaservice.domain;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author andrii
 */
@Entity
public class RegistratedCustomer extends Customer{
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public RegistratedCustomer() {
        creationDate = new Date();
    }

    public Date getDate() {
        return creationDate;
    }

    public void setDate(Date date) {
        this.creationDate = date;
    }
    
    
}
