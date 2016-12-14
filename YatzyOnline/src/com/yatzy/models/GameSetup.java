package com.yatzy.models;

import com.yatzy.dice.Dice;
import com.yatzy.engine.GameEngine;
import com.yatzy.player.Computer;
import com.yatzy.player.Human;
import com.yatzy.player.Player;
import com.yatzy.rules.GameRules;
import com.yatzy.rules.OptionalRules;
import com.yatzy.rules.UpDownRules;
import com.yatzy.rules.UpThenDownRules;

public class GameSetup {
	
	private Player[] players;
	private GameRules gameRules;
	private GameEngine gEngine;
	private int nbrOfPlayers;	
	private int turnNumber;
	private int activePlayer;
	private int nbrRoll;
	private Dice dice;
	
	
	public GameSetup(String sortpl1, String player1_firstName, String player1_lastName,
					 String sortpl2, String player2_firstName, String player2_lastName,
					 String rules, int nbrOfPlayers) {
			
		this.setnbrOfPlayers(nbrOfPlayers);
		this.players = new Player[this.getnbrOfPlayers()];
		
		System.out.println("Player 0 skall vara " + sortpl1);
		System.out.println("Player 1 skall vara " + sortpl2);
		
		switch(sortpl1) {
			case "human" :		this.players[0] = new Human(player1_firstName, player1_lastName, 1, 10, false);
								break;
			case "computer" : 	this.players[0] = new Computer(player1_firstName, player1_lastName, 1, 10 , false);
								break;
			default :			System.out.println("Seriously wrong setting player 0");					
		}
		
		switch(sortpl2) {
			case "human" :		this.players[1] = new Human(player2_firstName, player2_lastName, 1, 10, false);
								break;
			case "computer" : 	this.players[1] = new Computer(player2_firstName, player2_lastName, 1, 10 , false);
								break;
			default :			System.out.println("Seriously wrong setting player 0");					
		}
		
		System.out.println("Rules " + rules);
		
		switch(rules) {
			case "ruleOne" : 	this.gameRules = new UpDownRules();
								System.out.println("1Rules");
								break;
			case "ruleTwo" :	this.gameRules = new UpThenDownRules();
								System.out.println("2Rules");
								break;
			default :			this.gameRules = new OptionalRules();
								System.out.println("3Rules");
								break;		
		}
		
	}
	
	public void startNewWebGame(){
		gEngine = new GameEngine(this.players, this.gameRules, this.nbrOfPlayers);
		gEngine.startNewWebGame();
		
	}
	
	public void updateEngine(boolean[] lockDice, String diceAction) {
		int saveIndex = -1;
		boolean rollDice = false;
		if (diceAction != null) {
			switch (diceAction) {
				case "Roll dices" : rollDice = true;
									System.out.println("Roll Dices have been pressed!");
						  			break;
				case "Continue" :	rollDice = true;
									System.out.println("Roll Dices have been pressed!");
									break;		  			
				case "Ones" :		saveIndex = 1;
					  				break;
				case "Twos" :		saveIndex = 2;
					  				break;	  
				case "Threes" :		saveIndex = 3;
					  				break;	  
				case "Fours" :		saveIndex = 4;
					  				break;	
				case "Fives" :		saveIndex = 5;
				  					break;
				case "Sixes" :		saveIndex = 6;
				  					break;	  
				case "One Pair" :	saveIndex = 7;
				  					break;	
				case "Two pairs" :	saveIndex = 8;
				  					break;	
				case "Three of a Kind" :	saveIndex = 9;
									break;
				case "Four of a Kind" :	saveIndex = 10;
									break;	  
				case "Small Straight" : 	saveIndex = 11;
									break;	
				case "Large Straight" :	saveIndex = 12;
									break;	
				case "Full House" :	saveIndex = 13;
									break;
				case "Chance" :		saveIndex = 14;
									break;	  
				case "Yatzy" :		saveIndex = 15;
									break;						
			}
		}
		/*
		if (players[0] instanceof Human)
			System.out.println("Player 0 är human!");
		if (players[0] instanceof Computer)
			System.out.println("Player 0 är computer!");
		if (players[1] instanceof Human)
			System.out.println("Player 1 är human!");
		if (players[1] instanceof Computer)
			System.out.println("Player 1 är computer!");
		*/
		gEngine.calcNextStepWebGame(rollDice, saveIndex, lockDice);
		
	}
	
	public GameEngine getEngine() {
		return gEngine;
	}
	
	public int getResult(int playerId, int index) {
		return players[playerId].getScoreBoardResult(index);
	}
	
	public String getFirstName(int id) {
		return players[id].getFirstName();
	}

	public void setFirstName(int id, String firstName) {
		players[id].setFirstName(firstName);
	}
	
	public String getLastName(int id) {
		return players[id].getLastName();
	}

	public void setLastName(int id, String lastName) {
		players[id].setLastName(lastName);
	}
	
	public String getName(int id) {
		return players[id].getFirstName() + " " + players[id].getLastName();
	}
	
	public void displayPlayer(int id) {
		players[id].displayScoreBoard();
	}
	
	public GameRules getGameRules() {
		return gameRules;
	}
	
	public void setGameRules(GameRules rules) {
		this.gameRules = rules;
	}
	
	public int getnbrOfPlayers() {
		return this.nbrOfPlayers;
	}

	public void setnbrOfPlayers(int nbrPlay) {
		this.nbrOfPlayers = nbrPlay;
	}
	
	public int[][] getScoreBoard(int id) {
		return players[id].getScoreBoard();
	}
	
	
	
}
