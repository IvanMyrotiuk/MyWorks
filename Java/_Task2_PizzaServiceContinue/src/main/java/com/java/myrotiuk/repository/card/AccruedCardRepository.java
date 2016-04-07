package com.java.myrotiuk.repository.card;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

public interface AccruedCardRepository {
	int saveCard(AccruedCard card);
	int updateCard(AccruedCard card);
	AccruedCard getAccruedCardByCustomer(Customer customer);
}
