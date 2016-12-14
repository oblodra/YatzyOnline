package com.yatzy.player;

public class Human extends Player{
	private int thinkingTime;
	private boolean timeLimit;
	
	public Human() {
		super();
		this.thinkingTime = 30;
		this.timeLimit = true; // default is 30 seconds timelimit!!!
	}
	
	public Human(String firstName, String lastName, int playerColor, int thinkingTime, boolean timeLimit) {
		super(firstName, lastName, playerColor);
		this.thinkingTime = thinkingTime;
		this.timeLimit = timeLimit;
	}
	
	public int getThinkingTime() {
		return this.thinkingTime;
	}
	
	public void setThinkingTime(int thinkingTime) {
		this.thinkingTime = thinkingTime;
	}
	
	public boolean getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(boolean timeLimit) {
		this.timeLimit = timeLimit;
	}
}
