package com.java.myrotiuk.service.card;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;
import com.java.myrotiuk.repository.card.AccruedCardRepository;

@Service("cardService")
public class SimpleAccruedCardService implements AccruedCardService {
	
	private AccruedCardRepository cardRepository;
	
	@Autowired
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
	
	public long giveCardToCustomer(Customer customer, String name){
		System.out.println("CUSTOMERRRRERRR"+customer);
		return cardRepository.insert(new AccruedCard(customer, name));
	}
	
	public void updateCard(AccruedCard accruedCard){
		cardRepository.update(accruedCard);
	}

}
