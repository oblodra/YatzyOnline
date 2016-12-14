package com.yatzy.models;
import java.util.Random;

public class GameData {

	private int turn;
	private int buttonID;
	private int activePlayer;
	private String Player1FirstName;
	private String Player1LastName;
	private String Player2FirstName;
	private String Player2LastName;
	private int[][] player1ScoreBoard;
	private int[][] player2ScoreBoard;
	
	private int[][] webData;
	
	private int[] diceResult;
	private int[] gameOverData;
	
	private boolean[] diceLock;
	//not sure if savePossible is needed
	private boolean savePossible, buttonActive, diceLockActive, gameover;
	
	//test
	private Random rn = new Random();
	
	public GameData(String p1firstname, String p1lastname, String p2firstname, String p2lastname, int turn, int actPlayer) {
		
		//scoreBoard [0][...] -> what savebuttons are available when you can save
		//scoreBoard [0][...] -> current points, just to display
		this.player1ScoreBoard = new int[2][15];
		this.player2ScoreBoard = new int[2][15];
		this.webData = new int[3][17];
		this.diceResult = new int[5];
		this.diceLock = new boolean[5];
		//size nbrofplayers+1
		this.gameOverData = new int[2+1];
		
		//test
		for (int i = 0; i<5; i++)
			this.diceLock[i] = false;	
		for (int i =0; i<2; i++)
			for (int j=0; j<15; j++)
				this.webData[i][j] = 0;
		
		this.savePossible = false;
		this.diceLockActive = false;
		this.gameover = false;
		
		
		this.setPlayer1FirstName(p1firstname);
		this.setPlayer1LastName(p1lastname);
		this.setPlayer2FirstName(p2firstname);
		this.setPlayer2LastName(p2lastname);
		this.setTurn(turn);
		this.setActivePlayer(actPlayer);
		
		//test
		this.buttonActive= true;
		this.buttonID = 0;
		//this.savePossible = true;
	}
	
	
	
	public boolean isButtonActive(int id) {	
		return (this.webData[0][id] == 1)&&savePossible();

	}
	
	public void setGameOver(boolean gameover) {
		this.gameover = gameover;
	}
	
	public void setGameOverData(int[] data) {
		this.gameOverData = data.clone();
	}
	
	public int getGameOverData(int id){
		return gameOverData[id];
	}
	
	public void setWebData(int[][] wd) {
		this.webData = wd.clone();
	}
	
	public void setActivePlayer(int id) {
		this.activePlayer = id;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public void setDiceLocks(boolean[] dl) {
		this.diceLock = dl.clone();
	}
	
	public void setEnabledButtons(boolean[] enabledButtons) {
		buttonActive = enabledButtons[0];
		diceLockActive = enabledButtons[1];
		savePossible = enabledButtons[2];		
	}
	
	public void setDiceResult(int[] dr) {
		this.diceResult = dr.clone();
	}
	
	public void setButtonID(int ID) {
		this.buttonID = ID;
	}
	
	public String getButtonText() {
		switch(this.buttonID) {
			case 0 : return "value=\"Roll dices\"";

			case 1 : return "value=\"Continue\"";

			default: return "Error!";
		}
		
		
	}
	
	public int getDiceById(int id) {
		return this.diceResult[id];
	}

	public boolean isCheckBoxChecked(int id) {
		return diceLock[id];
	}
	
	public boolean isCheckBoxActive() {
		return this.diceLockActive;
	}
	public boolean isButtonActive() {
		return buttonActive;
	}
		
	public int getTurn() {
		return this.turn;
	}
	
	
	//if its the start of a turn its not possible to save the result, after rolling dices it will be possible and savePossible = true
	public boolean savePossible() {
		return savePossible;
	}
	
	public boolean getGameOver() {
		return this.gameover;
	}
	
	public int getActivePlayer() {
		return this.activePlayer;
	}
	
	public String getName(int id) {
		if (id == 0) 
			return getPlayer1FirstName() + " " + getPlayer1LastName();
		else
			return getPlayer2FirstName() + " " + getPlayer2LastName();
	}
	
	
	public String getPlayer1FirstName() {
		return Player1FirstName;
	}
		
	public void setPlayer1FirstName(String firstName) {
		Player1FirstName = firstName;
	}
	
	public String getPlayer1LastName() {
		return Player1LastName;
	}

	public void setPlayer1LastName(String lastName) {
		Player1LastName = lastName;
	}
	
	public String getPlayer1Name() {
		return this.getPlayer1FirstName() + " " + this.getPlayer1LastName();
	}
	
	public String getPlayer2FirstName() {
		return Player2FirstName;
	}

	public void setPlayer2FirstName(String firstName) {
		Player2FirstName = firstName;
	}
	
	public String getPlayer2LastName() {
		return Player2LastName;
	}

	public void setPlayer2LastName(String lastName) {
		Player2LastName = lastName;
	}
	
	public String getPlayer2Name() {
		return this.getPlayer2FirstName() + " " + this.getPlayer2LastName();
	}
		
	//testmethod for populating scoreBoard ad see that something actually happens when pressing the rolldice button
	
	/*
	public void updateScoreBoard() {
		for (int i = 0; i<15; i++) {
			this.player1ScoreBoard[1][i] = rn.nextInt(500);
			this.player2ScoreBoard[1][i] = rn.nextInt(500);
		}		
	}
	*/
	
	public int getTableData(int player, int id) {
		return this.webData[player+1][id];
	}
	
	/*
	public int getPlayer1ScoreBoardResultById(int id) {
		return this.player1ScoreBoard[1][id];
	}
	
	public int getPlayer2ScoreBoardResultById(int id) {
		return this.player2ScoreBoard[1][id];
	}
	*/
	
}
