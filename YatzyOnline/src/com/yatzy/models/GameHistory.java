package com.yatzy.models;

import java.util.Date;

public class GameHistory {
	private int GameID;
	private String WinnerFirstName;
	private String WinnerLastName;
	private int MaxScore;
	private String LoserFirstName;
	private String LoserLastName;
	private int MinScore;
	private Date date;
	

	public GameHistory(){}
	
	public GameHistory(	int gameid,
						String winfirstName, String winlastName, int maxscore,
						String losfirstName, String loslastName, int minscore,
						Date date) {
		
		this.setGameID(gameid);
		
		this.setWinnerFirstName(winfirstName);
		this.setWinnerLastName(winlastName);
		this.setMaxScore(maxscore);
		
		this.setLoserFirstName(losfirstName);
		this.setLoserLastName(loslastName);
		this.setMinScore(minscore);
		
		this.setDate(date);
	}

	public int getGameID() {
		return GameID;
	}

	public void setGameID(int gameid) {
		GameID = gameid;
	}
	
	public String getWinnerFirstName() {
		return WinnerFirstName;
	}

	public void setWinnerFirstName(String firstName) {
		WinnerFirstName = firstName;
	}
	
	public String getWinnerLastName() {
		return WinnerLastName;
	}

	public void setWinnerLastName(String lastName) {
		WinnerLastName = lastName;
	}	
	
	public int getMaxScore() {
		return MaxScore;
	}

	public void setMaxScore(int maxscore) {
		MaxScore = maxscore;
	}
	
	public String getLoserFirstName() {
		return LoserFirstName;
	}

	public void setLoserFirstName(String firstName) {
		LoserFirstName = firstName;
	}
	
	public String getLoserLastName() {
		return LoserLastName;
	}

	public void setLoserLastName(String lastName) {
		LoserLastName = lastName;
	}
	
	public int getMinScore() {
		return MinScore;
	}

	public void setMinScore(int minscore) {
		MinScore = minscore;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
