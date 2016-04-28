package com.java.myrotiuk.repository.card;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.BaseRepository;

public interface AccruedCardRepository extends BaseRepository<AccruedCard>{
	AccruedCard getAccruedCardByCustomer(Customer customer);
}
