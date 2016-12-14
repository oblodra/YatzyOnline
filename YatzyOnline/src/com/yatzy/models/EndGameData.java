package com.yatzy.models;

public class EndGameData {
	
	private String[] names;
	private int winnerID;
	private int[][] webData;
	
	public EndGameData(String[] playerNames, int[][] scoreData, int winID) {
		this.names = playerNames.clone();
		this.webData = scoreData.clone();
		this.winnerID = winID;
	}
	
	public int getTableData(int player, int id) {
		return this.webData[player+1][id];
	}
	
	public String getName(int id) {
		return names[id];
	}
		
	public int getWinnerId() {
		return this.winnerID;
	}
	
	//not needed
	public void setWebData(int[][] wd) {
		this.webData = wd.clone();
	}
	
}
