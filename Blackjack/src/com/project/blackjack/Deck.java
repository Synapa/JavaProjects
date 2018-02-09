package com.project.blackjack;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private ArrayList<Card> cards;
	

	public Deck(int i) {
		this.cards = new ArrayList<Card>();
		int x =0;
		
		do {
			makeDeck();
			shuffle();
			x++;
		}while(x<i);
	
	}
	
	public void makeDeck() {
		for(Suit suit: Suit.values()) {
			for(Value value: Value.values()) {
				this.cards.add(new Card(suit,value));
			}
		}
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void printDeck() {
		int i=1;
		for(Card singleCard : this.cards) {
			System.out.println(i + " - " + singleCard.cardToString());
			i++;
		}
		
	}
	public void shuffle() {
		Random rand = new Random();
		ArrayList<Card> temp = new ArrayList<Card>();
		
		int originalSize = this.cards.size();
		
		//shuffle deck by removing card via random index and adding to temp deck
		for (int i=0; i<originalSize; i++) {
			int  n = rand.nextInt((this.cards.size()-1 -0) + 1) + 0;
			temp.add(this.cards.get(n));
			this.cards.remove(n);
		}
		
		//set original deck equal to shuffled temp deck;
		this.cards = temp;
	}
	public Card draw() {
		Random rand = new Random();
		int  n = rand.nextInt((this.cards.size()-1 -0) + 1) + 0;
		Card drawnCard = this.cards.get(n);
		this.cards.remove(n);
		
		double tempCount = (double)Game.getInstance().getGameCount();
		
		if (drawnCard.getValue().getMask() >= 2 && drawnCard.getValue().getMask() <= 6) {
			tempCount += 1.0;
		} else if (drawnCard.getValue().getMask() >= 7 && drawnCard.getValue().getMask() <= 9) {
			// do nothing to count
		} else {
			tempCount -= 1.0;
		}
		Game.getInstance().setGameCount(tempCount);
		
		return drawnCard;
	}
	
}
