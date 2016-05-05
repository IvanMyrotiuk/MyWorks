package com.java.myrotiuk.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@ElementCollection
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
				for(Pizza p : pizzas.keySet()){
					if(p.getId() == id){
						pizzas.remove(p);
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean addPizzas(List<Pizza> additionalPizzas) {
		if (this.pizzas.size() + additionalPizzas.size() <= 10 && this.getOrderStatus() == OrderStatus.NEW) {
			for(Pizza p : additionalPizzas){
				Integer count = pizzas.get(p);
				if(count == null){
					pizzas.put(p, 1);
				}else{
					pizzas.put(p, count + 1);
				}
			}
			return true;
		}
		return false;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public double getOrderPrice() {
		if(orderPrice == 0){
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

	public Long getId() {
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
	public String toString() {
		return "Order [id=" + id + ", address=" + address + ", pizzas=" + Arrays.asList(pizzas.keySet().toArray()) + ", orderPrice=" + orderPrice
				+ ", orderStatus=" + orderStatus + "]";
	}
}
