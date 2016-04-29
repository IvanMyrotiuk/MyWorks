package com.java.myrotiuk.service.card;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

public interface AccruedCardService {
	Optional<AccruedCard> findCardByCustomer(Customer customer, EntityManager em);
	long giveCardToCustomer(Customer customer, String name,  EntityManager em);
	void updateCard(AccruedCard accruedCard,  EntityManager em);
}
