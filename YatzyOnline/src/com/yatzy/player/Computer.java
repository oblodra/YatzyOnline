package com.yatzy.player;

public class Computer extends Player{
	private int delayMoveTime;
	private boolean delay;
	
	
	public Computer() {
		super();
		this.delayMoveTime = 3;
		this.delay = true; // default is 3 seconds delay!!!
	}
	
	public Computer(String firstName, String lastName, int playerColor, int delayMoveTime, boolean delay) {
		super(firstName, lastName, playerColor);
		this.delayMoveTime = delayMoveTime;
		this.delay = delay;
	}
	
	public int getDelayMoveTime() {
		return this.delayMoveTime;
	}
	
	public void setDelayMoveTime(int delayMoveTime) {
		this.delayMoveTime = delayMoveTime;
	}
	
	public boolean getDelay() {
		return this.delay;
	}
	
	public void setDelay(boolean delay) {
		this.delay = delay;
	}
	
	
}
