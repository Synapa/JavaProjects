package com.project.blackjack.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.project.blackjack.Card;
import com.project.blackjack.Deck;
import com.project.blackjack.Hand;
import com.project.blackjack.Player;
import com.project.blackjack.Suit;
import com.project.blackjack.Value;

public class HandTests {
    Deck testDeck = new Deck(5);
    Card c1 = new Card(Suit.CLUBS, Value.EIGHT);
    Card c2 = new Card(Suit.CLUBS, Value.NINE);
    Card c3 = new Card(Suit.CLUBS, Value.TEN);
    Card c4 = new Card(Suit.CLUBS, Value.KING);
    Card c5 = new Card(Suit.HEARTS, Value.ACE);
    Hand testHand = new Hand(c1);
    
    @Test
    public void handTests() {
    	testHand.addCardToHand(c1);
        testHand.addCardToHand(c2);
        Player testPlayer = new Player();
        assertEquals("\nHand 1: 8C, 9C", testPlayer.handToString());
        
        Hand secondTestHand = new Hand(c3);
        secondTestHand.addCardToHand(c4);
        testPlayer.addNewHand(secondTestHand);
        assertEquals(2,testPlayer.getPlayerHands().size());
        assertEquals("\nHand 1: 8C, 9C\nHand 2: 10C, KC", testPlayer.handToString());
        
        assertEquals(17,testHand.getHandValue());
        assertEquals(20,secondTestHand.getHandValue());
        secondTestHand.addCardToHand(new Card(Suit.CLUBS, Value.ACE));
        assertEquals(21,secondTestHand.getHandValue());
        
        Hand thirdTestHand = new Hand(new Card(Suit.DIAMONDS, Value.ACE));
        thirdTestHand.addCardToHand(new Card(Suit.CLUBS, Value.ACE));
        thirdTestHand.addCardToHand(new Card(Suit.SPADES, Value.ACE));
        thirdTestHand.addCardToHand(c5);
        assertEquals(14,thirdTestHand.getHandValue());
        
        thirdTestHand.removeCardFromHand(c5);
        assertEquals(13, thirdTestHand.getHandValue());
        testPlayer.addNewHand(thirdTestHand);
        assertEquals("\nHand 1: 8C, 9C\nHand 2: 10C, KC, AC\nHand 3: AD, AC, AS", testPlayer.handToString());
    }
}
