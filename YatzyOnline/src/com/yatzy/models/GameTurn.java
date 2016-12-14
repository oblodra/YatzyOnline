package com.yatzy.models;

public class GameTurn {
	private int TurnNumber;
	private String FirstName;
	private String LastName;
	private int Dice1;
	private int Dice2;
	private int Dice3;
	private int Dice4;
	private int Dice5;
	private String ScoreType;
	private String PointsEndOfTurn;

	public GameTurn(){}
	
	public GameTurn(int turnNumber, 
					String firstName, String lastName, 
					int dice1, int dice2, int dice3, int dice4, int dice5,
					String scoreType, 
					String pointsEndOfTurn) {
		this.setTurnNumber(turnNumber);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDice1(dice1);
		this.setDice2(dice2);
		this.setDice3(dice3);
		this.setDice4(dice4);
		this.setDice5(dice5);
		this.setScoreType(scoreType);
		this.setPointsEndOfTurn(pointsEndOfTurn);
	}

	public int getTurnNumber() {
		return TurnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		TurnNumber = turnNumber;
	}
	
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	public int getDice1() {
		return Dice1;
	}

	public void setDice1(int dr) {
		Dice1 = dr;
	}
	
	public int getDice2() {
		return Dice2;
	}

	public void setDice2(int dr) {
		Dice2 = dr;
	}
	
	public int getDice3() {
		return Dice3;
	}

	public void setDice3(int dr) {
		Dice3 = dr;
	}
	
	public int getDice4() {
		return Dice4;
	}

	public void setDice4(int dr) {
		Dice4 = dr;
	}
	
	public int getDice5() {
		return Dice5;
	}

	public void setDice5(int dr) {
		Dice5 = dr;
	}
	
	public String getScoreType() {
		return ScoreType;
	}

	public void setScoreType(String scoreType) {
		ScoreType = scoreType;
	}
	
	public String getPointsEndOfTurn() {
		return PointsEndOfTurn;
	}

	public void setPointsEndOfTurn(String pointsEndOfTurn) {
		PointsEndOfTurn = pointsEndOfTurn;
	}
	

}
