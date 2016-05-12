package com.java.myrotiuk.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.java.myrotiuk.exception.StatusOrderException;
import com.java.myrotiuk.infrustructure.Domain;

//@Component
@Domain
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity(name = "OrderPizza")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "address_id")
	private Address address;
	
	@ElementCollection(fetch = FetchType.EAGER)
	//@JoinTable(name = "Order_Pizza",joinColumns = {@JoinColumn(name = "Order_id")})
	@CollectionTable(name = "Order_Pizza", joinColumns = {@JoinColumn(name = "Order_id")})
	@MapKeyJoinColumn(name = "Pizza_id")
	@Column(name = "Quantity")
	private Map<Pizza, Integer> pizzas;
	
	private double orderPrice;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	public Order() {
		this.orderStatus = OrderStatus.NEW;
	}

	public enum OrderStatus {
		NEW {
			@Override
			OrderStatus next() {
				return IN_PROGRESS;
			}

			OrderStatus cancel() {
				return CANCELED;
			}
		},
		IN_PROGRESS {
			@Override
			OrderStatus next() {
				return OrderStatus.DONE;
			}

			OrderStatus cancel() {
				throw new StatusOrderException("You can not switch"+IN_PROGRESS+" status to "+CANCELED);
			}
		},
		CANCELED {
			@Override
			OrderStatus next() {
				throw new StatusOrderException("You can not switch"+CANCELED+" status to something else");
			}

			OrderStatus cancel() {
				return OrderStatus.CANCELED;
			}
		},
		DONE {
			@Override
			OrderStatus next() {
				throw new StatusOrderException("You can not switch"+OrderStatus.DONE+" status to something else");
			}

			OrderStatus cancel() {
				throw new StatusOrderException("You can not switch"+OrderStatus.DONE+" status to something" + OrderStatus.CANCELED);
			}
		};
		abstract OrderStatus cancel();
		abstract OrderStatus next();
	}
	
	public Order next(){
		this.orderStatus = orderStatus.next();
		return this;
	}
	
	public Order cancel(){
		this.orderStatus = orderStatus.cancel();
		return this;
	}

	public boolean changeOrderDeletePizza(Integer... pizzasID) {
		if (this.getOrderStatus() == OrderStatus.NEW ) {

			for(Integer id: pizzasID){
				Iterator<Pizza> iter = pizzas.keySet().iterator();
				while(iter.hasNext()){
					Pizza pizzaToDelete = iter.next();
					if(pizzaToDelete.getId() == id){
						iter.remove();
					}
				}
			}
			return true;
		}
		return false;
	}

	public void addPizzas(List<Pizza> additionalPizzas) {
			for(Pizza p : additionalPizzas){
				Integer count = pizzas.get(p);
				if(count == null){
					pizzas.put(p, 1);
				}else{
					pizzas.put(p, count + 1);
				}
			}
	}

	public int sizeOrder(){
		int q = 0;
		for(Pizza p:pizzas.keySet()){
			Integer n = pizzas.get(p);
			q += n;
		}
		return q;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public double getOrderPrice() {
		if(orderPrice == 0 && this.orderStatus == OrderStatus.DONE){
			countOrderPrice();
		}
		return orderPrice;
	}

	public double countOrderPrice() {
		for (Pizza pizza : pizzas.keySet()) {
			Integer count = pizzas.get(pizza);
			orderPrice += pizza.getPrice() * count;
		}
		return orderPrice;
	}

	public void setOrderStatus(OrderStatus status) {
		this.orderStatus = status;
	}

	public long getId() {
		return id;
	}

	public Map<Pizza, Integer> getPizzas() {
		return pizzas;
	}

	public void setPizzas(Map<Pizza, Integer> pizzas) {
		this.pizzas = pizzas;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((pizzas == null) ? 0 : pizzas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id != other.id)
			return false;
		if (pizzas == null) {
			if (other.pizzas != null)
				return false;
		} else if (!pizzas.equals(other.pizzas))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", address=" + address + ", pizzas=" + Arrays.asList(pizzas.keySet().toArray()) 
		+ " count respectively: "+Arrays.asList(pizzas.values().toArray()) + ", orderPrice=" + orderPrice 
				+ ", orderStatus=" + orderStatus + "]";
	}
}
