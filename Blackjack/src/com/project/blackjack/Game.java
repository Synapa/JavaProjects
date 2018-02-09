package com.project.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Game {
	private double gameCount = 0;
	private static volatile Game instance = null;
	private Deck gameDeck;
	private Player player;
	private Dealer dealer;
	private int initialNoOfDecks = 0;
	private boolean continueHit = true;
	private boolean postStand = false;

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getNumberOfDecks() {
		if (initialNoOfDecks != 1) {
			return gameDeck.getCards().size() / 52.0;
		} else {
			return 1;
		}

	}

	public Deck getGameDeck() {
		return gameDeck;
	}

	public void setGameDeck(Deck cards) {
		this.gameDeck = cards;
	}

	public double getGameCount() {
		return gameCount;
	}

	public void setGameCount(double tempCount) {
		this.gameCount = tempCount;

	}

	private Game() {

	}

	public static Game getInstance() {
		if (instance == null) {
			synchronized (Game.class) {
				if (instance == null) {
					instance = new Game();
				}
			}
		}
		return instance;
	}

	public void gameLoop() {
		boolean continueRunning = true;

		// player.addNewHand(new Hand(instance.gameDeck.draw()));
		// player.getPlayerHands().get(1).addCardToHand(instance.getGameDeck().draw());

		while (continueRunning) {
			getInputForPlayerStake();
			initialiseStartingHands();
			printGameInfo();
			getInputForPlayerChoice();

			resetHands();

		}
	}

	public void resetHands() {
		instance.player.getPlayerHands().clear();
		instance.player.setNoOfHands(0);

	}

	public void initialiseStartingHands() {
		instance.getPlayer().addNewHand(new Hand(instance.gameDeck.draw()));
		instance.dealer.setDealersHand(new Hand(instance.gameDeck.draw()));
		instance.player.getPlayerHands().get(0).addCardToHand(instance.gameDeck.draw());
		instance.dealer.getDealersHand().addCardToHand(instance.gameDeck.draw());
	}

	public void getInputForPlayerStake() {
		postStand = false;
		boolean testInputAgain = true;
		Scanner sc = new Scanner(System.in);
		centeredTextGui("Input Your Stake, Stake Must Be An Integer and Min Stake = 1", '-');
		do {

			try {
				int input = sc.nextInt();
				if (input > 0 && input <= instance.getPlayer().getPlayerBalance()) {
					testInputAgain = false;
					instance.getPlayer().setPlayerStake(input);
				} else {
					centeredTextGui("Stake Must Be An Integer and Min Stake = 1", '-');
				}
			} catch (InputMismatchException ime) {
				centeredTextGui("Input For No. Of Decks Must Be An Integer & Be Between 1-10", '-');
				sc.nextInt();
			}
		} while (testInputAgain);
	}

	public void getInputForPlayerChoice() {
		boolean testInputAgain = true;
		
		Scanner sc = new Scanner(System.in);
		centeredTextGui("1 = Stand || 2 = Hit || 3 = Split || 4 = Exit", 'x');
		do {

			try {
				int input = sc.nextInt();
				if (input > 0 && input <= 4) {
					testInputAgain = false;
					instance.getPlayer().setPlayerStake(input);

					switch (input) {
					case 1:
						if (instance.player.getNoOfHands() == 1) {
							checkPlayerHand(0);
							printGameInfo();
						} else {
							System.out.println("DIDMT MAKE IT");
						}
						break;
					case 2:
						hit();
						printGameInfo();
						break;
					case 3:
						split();
						break;
					case 4:
						System.exit(0);
						break;

					}
				} else {
					centeredTextGui("Please Select A Valid Option: 1 = Stand || 2 = Hit || 3 = Split || 4 = Exit", 'x');
				}
			} catch (InputMismatchException ime) {
				centeredTextGui("Please Select A Valid Option: 1 = Stand || 2 = Hit || 3 = Split || 4 = Exit", 'x');
				sc.nextInt();
			}
		} while (testInputAgain);
	}

	private void split() {
		System.out.println("SPLIT");

	}

	private void hit() {
		continueHit = true;
		do {
			if (instance.player.getNoOfHands() > 1) {

			} else {
				instance.player.getPlayerHands().get(0).addCardToHand(instance.gameDeck.draw());
				checkPlayerHand(0);
			}
		} while (continueHit);
		
	}

	private void checkPlayerHand(int handNumber) {
		postStand = true;
		int handValue = instance.player.getPlayerHands().get(handNumber).getHandValue();
		
		if (handValue > 21) {
			centeredTextGui("Player Wins", '*');
			instance.player.setPlayerBalance((instance.player.getPlayerBalance() - instance.player.getPlayerStake()));
			continueHit = false;
		} else if (handValue == 21 && instance.dealer.getDealersHand().getHandValue() == 21) {
			centeredTextGui("Player Draws", '*');
			continueHit = false;
		} else if (handValue == 21 && instance.dealer.getDealersHand().getHandValue() != 21) {
			centeredTextGui("Player Wins", '*');
			instance.player.setPlayerBalance((instance.player.getPlayerBalance() + instance.player.getPlayerStake()));
			continueHit = false;
		} else {
			checkPlayerHandsVsDealer(instance.player.getPlayerHands().get(0).getHandValue());
		}
	}

	private void checkPlayerHandsVsDealer(int pHandValue) {
		int dealerHandValue = instance.dealer.getDealersHand().getHandValue();
		if (dealerHandValue > 21) {
			centeredTextGui("Player Wins", '*');
			instance.player.setPlayerBalance((instance.player.getPlayerBalance() + instance.player.getPlayerStake()));
		} else if (dealerHandValue >= 17 && dealerHandValue > pHandValue) {
			centeredTextGui("Player Loses", '*');
			instance.player.setPlayerBalance((instance.player.getPlayerBalance() - instance.player.getPlayerStake()));
		} else if (dealerHandValue >= 17 && dealerHandValue == pHandValue) {
			centeredTextGui("Player Draws", '*');
		} else if (dealerHandValue >= 17 && dealerHandValue < pHandValue) {
			centeredTextGui("Player Wins", '*');
			instance.player.setPlayerBalance((instance.player.getPlayerBalance() + instance.player.getPlayerStake()));
		}else if (dealerHandValue < 17) {
			dealerDraw();
		}
	}

	private void dealerDraw() {
		boolean dealerDraw = true;

		while (dealerDraw) {
			Card cardDrawn = instance.gameDeck.draw();
			instance.dealer.getDealersHand().addCardToHand(cardDrawn);
			centeredTextGui("Dealer Draws: " + cardDrawn.cardToString(), '*');
			int tempDealerValue = instance.dealer.getDealersHand().getHandValue();
			if (tempDealerValue > 21) {
				centeredTextGui("Player Wins", 'x');
				instance.player.setPlayerBalance((instance.player.getPlayerBalance() + instance.player.getPlayerStake()));
				dealerDraw = false;
			} else if (tempDealerValue >= 17) {
				dealerDraw = false;
				if (instance.player.getNoOfHands() > 1) {
					for(int i =0; i< instance.player.getNoOfHands(); i++) {
						checkPlayerHandsVsDealer(instance.player.getPlayerHands().get(i).getHandValue());
					}
				} else {
					checkPlayerHandsVsDealer(instance.player.getPlayerHands().get(0).getHandValue());
				}
			} else {
				// do nothing
			}
		}

	}

	public void initialiseGame() {

		boolean testInputAgain = true;
		centeredTextGui("Input Number Of Decks You Wish To Play With", '-');
		Scanner sc = new Scanner(System.in);
		do {

			try {
				int input = sc.nextInt();
				if (input > 0 && input < 11) {
					testInputAgain = false;
					initialNoOfDecks = input;
				} else {
					centeredTextGui("Number Of Decks Must Be Between 1-10", '-');
				}
			} catch (InputMismatchException ime) {
				centeredTextGui("Input For No. Of Decks Must Be An Integer & Be Between 1-10", '-');
				sc.nextInt();
			}
		} while (testInputAgain);
		testInputAgain = true;
		Deck deck = new Deck(initialNoOfDecks);
		instance.setGameDeck(deck);
		instance.setPlayer(new Player());
		instance.setDealer(new Dealer());
		player.setPlayerBalance(100);
		gameLoop();

	}

	public void centeredTextGui(String s, char c) {
		String temp = "";
		for (int i = 0; i < (200 - s.length()); i++) {
			if (i == ((200 - s.length()) / 2)) {
				if (s.equals("")) {
					temp += s;
				} else {
					temp += " ";
					temp += s;
					temp += " ";
				}

			}
			temp += c;
		}
		System.out.println(temp);
	}

	public void printGameInfo() {
		centeredTextGui("Game Info", '=');
		if(!postStand) {
			centeredTextGui(
					"Player Infomation ||  Player Stake: " + this.player.getPlayerStake() + " || Player Balance: "
							+ this.player.getPlayerBalance() + " || Game Count: " + instance.gameCount / getNumberOfDecks(),
					'-');
			centeredTextGui("Player Hands ||  " + this.player.handToString() + " || " + playerHandsValue(), '-');
			centeredTextGui("Dealer Showing: " + this.dealer.getDealersHand().getHand().get(0).cardToString() + " || Hand Value: "
					+ this.dealer.getDealersHand().getFirstCardValue(), '-');
			centeredTextGui("Dealer Real: " + this.dealer.handToString() + " || Hand Value: "
					+ this.dealer.getDealersHand().getHandValue(), '-');
		}
		else {
			centeredTextGui(
					"Player Infomation ||  Player Stake: " + this.player.getPlayerStake() + " || Player Balance: "
							+ this.player.getPlayerBalance() + " || Game Count: " + instance.gameCount / getNumberOfDecks(),
					'-');
			centeredTextGui("Player Hands ||  " + this.player.handToString() + " || " + playerHandsValue(), '-');
			centeredTextGui("Dealer Showing: " + this.dealer.handToString() + " || Hand Value: "
					+ this.dealer.getDealersHand().getHandValue(), '-');
		}

		centeredTextGui("", '=');
		
		
		System.out.println("");
	}

	public String playerHandsValue() {
		int noOfHands = instance.player.getNoOfHands();
		String output = "";
		if (noOfHands == 1) {
			output += "Hand Value: " + this.player.getPlayerHands().get(0).getHandValue();
		} else {
			for (int i = 0; i < noOfHands; i++) {
				output += " || Hand Value " + (i + 1) + ": " + this.player.getPlayerHands().get(i).getHandValue();
			}
		}
		return output;

	}

}
