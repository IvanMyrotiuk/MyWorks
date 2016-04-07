package com.java.myrotiuk.service.card;

import java.util.Optional;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.card.AccruedCardRepository;

public class SimpleAccruedCardService implements AccruedCardService {

	private AccruedCardRepository cardRepository;
	
	public SimpleAccruedCardService(AccruedCardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	
	@Override
	public Optional<AccruedCard> findCardByCustomer(Customer customer) {
		
		AccruedCard accruedCard = cardRepository.getAccruedCardByCustomer(customer);

		if(accruedCard != null){
			return Optional.of(accruedCard);
		}else{
			return Optional.empty();
		}
	}
	
	public int giveCardToCustomer(Customer customer){
		return cardRepository.saveCard(new AccruedCard(customer));
	}
	
	public int updateCard(AccruedCard accruedCard){
		return cardRepository.updateCard(accruedCard);
	}

}
