package com.yatzy.rules;

public abstract class GameRules {
	
	private int[][] orderOfPlay;
	private int[][] scoreMatrix;
	private int nbrOfDices;
	private int nbrOFTurns;
	
	public GameRules() {
		scoreMatrix = new int[79][9];
	}
	
	public int[] getTurnRules(int turn) {
		return this.orderOfPlay[turn];
	}
	
	public int[][] getScoreMatrix() {
		return scoreMatrix;
	}
	
	public int getNbrOfDices() {
		return this.nbrOfDices;
	}
	
	protected void setNbrOfDices(int number) {
		this.nbrOfDices = number;
	}
	
	public int getNbrOfTurns() {
		return this.nbrOFTurns;
	}
	
	protected void setNbrOfTurns(int number) {
		this.nbrOFTurns = number;
	}
	
	
	protected void setOrderOfPlay(int[][] oOP) {
		this.orderOfPlay = oOP;
	}
	
	protected void setScoreMatrix(int[][] sM) {
		this.scoreMatrix = sM;
	}
	
	public abstract String lookupScoreType(int id);
	
	public int getSaveLocThisTurnById(int turn, int id) {
		return orderOfPlay[turn][id];
	}
}
