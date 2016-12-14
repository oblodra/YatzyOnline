package com.yatzy.models;

public class Player {
	private int PlayerID;
	private String FirstName;
	private String LastName;

	public Player(){}
	
	public Player(int playerID, String firstName, String lastName) {
		this.setPlayerID(playerID);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Player(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public int getPlayerID() {
		return PlayerID;
	}
	
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	public void setPlayerID(int playerID) {
		PlayerID = playerID;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
}
