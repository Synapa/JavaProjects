package com.project.blackjack.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.project.blackjack.Card;
import com.project.blackjack.Deck;
import com.project.blackjack.Game;
import com.project.blackjack.Hand;
import com.project.blackjack.Player;
import com.project.blackjack.Suit;
import com.project.blackjack.Value;

public class GameTests {
	Deck testDeck2 = new Deck(5);
    Card c3 = new Card(Suit.CLUBS, Value.TEN);
    Card c4 = new Card(Suit.CLUBS, Value.KING);
    Card c5 = new Card(Suit.HEARTS, Value.ACE);
	Player player = new Player();
	
    @Test
    public void checkNumberOfDecks() {
        Game.getInstance().setGameDeck(testDeck2);
        testDeck2.draw();
        assertEquals(5, Game.getInstance().getNumberOfDecks());
    }
    
    @Test
    public void checkGameCount() {
        Game.getInstance().setGameDeck(testDeck2);
        player.addNewHand(new Hand(c3));
        player.getPlayerHands().get(0).addCardToHand(c3);
        player.getPlayerHands().get(0).addCardToHand(c3);
        player.getPlayerHands().get(0).addCardToHand(c3);
        player.getPlayerHands().get(0).addCardToHand(c3);
        player.getPlayerHands().get(0).addCardToHand(c3);
        assertEquals(-1, Game.getInstance().getGameCount());
    }
    @Test
    public void testGameLoop() {
    	Game.getInstance().gameLoop();
    }
    
}
