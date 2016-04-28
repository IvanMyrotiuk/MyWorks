package com.java.myrotiuk.repository.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.java.myrotiuk.domain.AccruedCard;
import com.java.myrotiuk.domain.Customer;

@Repository("cardRepository")
public class InMemAccruedCardRepository implements AccruedCardRepository{

	private List<AccruedCard> cards = new ArrayList<>();
	
	@Override
	public long insert(AccruedCard card) {
		cards.add(card);
		return card.getId();
	}

	@Override
	public void update(AccruedCard card) {
		long id = 0;
		for(AccruedCard c : cards){
			if(c.getId() == card.getId()){
				c = card;
				id = c.getId();
			}
		}
		//return id;
	}

	@Override
	public AccruedCard getAccruedCardByCustomer(Customer customer) {
		
		AccruedCard accCard = null;
		
		for(AccruedCard card: cards){
			Customer customerOfCard = card.getCustomer();
			if(customerOfCard.getId() == customer.getId()){
				accCard = card;
				break;
			}
		}
		return accCard;
	}

	@Override
	public List<AccruedCard> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccruedCard find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
}
