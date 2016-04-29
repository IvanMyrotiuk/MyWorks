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
	public Optional<AccruedCard> findCardByCustomer(Customer customer, EntityManager em) {
		
		AccruedCard accruedCard = cardRepository.getAccruedCardByCustomer(customer, em);

		if(accruedCard != null){
			return Optional.of(accruedCard);
		}else{
			return Optional.empty();
		}
	}
	
	public long giveCardToCustomer(Customer customer, String name,  EntityManager em){
		return cardRepository.insert(new AccruedCard(customer, name), em);
	}
	
	public void updateCard(AccruedCard accruedCard,  EntityManager em){
		cardRepository.update(accruedCard, em);
	}

}
