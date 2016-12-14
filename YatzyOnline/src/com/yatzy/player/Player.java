package com.yatzy.player;

public abstract class Player {
	
	private int[][] scoreBoard;
	private String firstName;
	private String lastName;
	private int playerColor;
	
	
	public Player() {
		scoreBoard = new int[2][15]; 
		//playerColor = 1; // 1 red, 2 yellow, 3 green, 4 blue, 5 black
		initiate();
	}
	

	public Player(String firstName, String lastName, int playerColor) {
		this.scoreBoard = new int[2][15];
		this.firstName = firstName;
		this.lastName = lastName;
		this.playerColor = playerColor;
		initiate();
	}
	
	private void initiate() {
		for (int i = 0; i<15; i++) {
			this.scoreBoard[0][i] = 1;
			this.scoreBoard[1][i] = 0;
		}
	}
	
	public String getName() {
		return (this.firstName + " " + this.lastName);
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public int getPlayerColor() {
		return playerColor;
	}
	
	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}
	
	public int[] getScoreSetLeft() {
		return scoreBoard[0];
	}
	
	public void updateScoreBoard(int index, int points) {
		this.scoreBoard[0][index] = 0;
		this.scoreBoard[1][index] = points;
	}
	
	public int[][] getScoreBoard() {
		return this.scoreBoard;
	}
	
	public int getSaveLocAvailable(int id) {
		return this.scoreBoard[0][id];
	}
	
	public int getScoreBoardResult(int id) {
		return this.scoreBoard[1][id];
	}
	
	public void displayScoreBoard() {
		System.out.println("\nScoreBoard for player " + this.getFirstName() + " " + this.getLastName() +"\n");
		System.out.println("Points: ");
		for (int i = 0; i< 11; i=i+5) {
			for (int j = 0; j<5; j++) {
				System.out.print("[" +  (i+j+1) + "]" + ":  " + scoreBoard[1][i+j] + "  ");
								
			}
			System.out.println();
		}
		
		System.out.println();
	}
}
