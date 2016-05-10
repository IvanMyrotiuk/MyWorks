package com.java.myrotiuk.service.card;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

public interface AccruedCardService {
	Optional<AccruedCard> findCardByCustomer(Customer customer);
	long awardCard(Customer customer, String name);
	void updateCard(AccruedCard accruedCard);
}
