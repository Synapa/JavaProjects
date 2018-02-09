package com.project.blackjack;

import java.util.ArrayList;

public class Player {

	private ArrayList<Hand> playerHands;
	private int noOfHands = 0;
	private int playerStake =0;
	private double playerBalance = 0;
	

	public Player() {
		this.playerHands = new ArrayList<Hand>();
		
	}
	
	
	public int getPlayerStake() {
		return playerStake;
	}

	public void setPlayerStake(int playerStake) {
		this.playerStake = playerStake;
	}

	public int getNoOfHands() {
		return noOfHands;
	}

	public void setNoOfHands(int noOfHands) {
		this.noOfHands = noOfHands;
	}

	public ArrayList<Hand> getPlayerHands() {
		return playerHands;
	}

	public double getPlayerBalance() {
		return playerBalance;
	}

	public void setPlayerBalance(double playerBalance) {
		this.playerBalance = playerBalance;
	}

	public void addNewHand(Hand hand) {
		this.playerHands.add(hand);
		this.noOfHands+=1;
	}
	
	public String handToString() {
		String currentHand ="";
		for(int j=0; j< this.playerHands.size(); j++) {
			if(j!=0) {
				currentHand+=("|| Hand " + (j+1) + ": " );
			}
			else {
				currentHand+=(" Hand " + (j+1) + ": " );
			}
			
			for(int i=0; i<this.playerHands.get(j).getHand().size(); i++) {
				currentHand+=this.playerHands.get(j).getHand().get(i).cardToString();
				if(!(i==this.playerHands.get(j).getHand().size()-1)) {
					currentHand+= ", ";
				}
				
			}
			
		}
		return currentHand;

	}
	
}
