package com.java.myrotiuk.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.java.myrotiuk.exception.StatusOrderException;

public class Order {
	private Long id;
	private Customer customer;
	private List<Pizza> pizzas;
	private static long currentId;
	private double orderPrice;
	private OrderStatus orderStatus;
	
	public Order(Customer customer, List<Pizza> pizzas) {
		this.customer = customer;
		this.pizzas = pizzas;
		this.orderStatus = OrderStatus.NEW;
		this.id = ++currentId;
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

			Pizza[] pizzasToSort = (Pizza[]) pizzas.toArray();
			Arrays.sort(pizzasToSort, new Comparator<Pizza>() {
				public int compare(Pizza z1, Pizza z2) {
					return z1.getId() - z2.getId();
				}
			});

			for (Integer toDelete : pizzasID) {
				int del = Arrays.binarySearch(pizzasToSort, toDelete);
				pizzasToSort[del] = null;
			}

			List<Pizza> newPizzas = new ArrayList<>();
			for (Pizza pizzaToSet : pizzasToSort) {
				if(pizzasToSort != null){
					newPizzas.add(pizzaToSet);
				}
			}
			pizzas = newPizzas;
			return true;
		}
		return false;
	}

	public boolean addPizzas(List<Pizza> additionalPizzas) {
		if (this.pizzas.size() + additionalPizzas.size() <= 10) {
			this.pizzas.addAll(additionalPizzas);
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
		return countOrderPrice();
	}

	public double countOrderPrice() {
		for (Pizza pizza : pizzas) {
			orderPrice += pizza.getPrice();
		}
		return orderPrice;
	}

	public void setOrderStatus(OrderStatus status) {
		this.orderStatus = status;
	}

	public Long getId() {
		return id;
	}

	// public void setId(Long id) {
	// this.id = id;
	// }
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", pizzas=" + pizzas + "]";
	}
}
