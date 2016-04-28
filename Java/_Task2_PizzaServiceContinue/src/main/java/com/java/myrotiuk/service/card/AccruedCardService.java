package com.java.myrotiuk.service.card;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

public interface AccruedCardService {
	Optional<AccruedCard> findCardByCustomer(Customer customer);
	long giveCardToCustomer(Customer customer, String name);
	void updateCard(AccruedCard accruedCard);
}
