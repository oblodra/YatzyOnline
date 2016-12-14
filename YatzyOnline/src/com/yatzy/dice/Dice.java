package com.yatzy.dice;

import java.util.Random;

public class Dice {
	
	private int value;
	private boolean locked;
	private Random rn = new Random();
	
	public Dice() {
		this.unlockDice();
		this.setValue(1);
		System.out.println("1Dice initiaded!");
	}
	
	public Dice(boolean locked, int value) {
		this.setLock(locked);
		this.setValue(value);
		System.out.println("2Dice initiaded!");
	}
	
	public void resetDice() {
		this.locked = false;
		this.value = 1; 
	}
	
	public void reRoll() {
		if (!this.locked)
			this.value = rn.nextInt(6)+1;
	}

	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public void lockDice() {
		this.locked = true;
	}

	public void unlockDice() {
		this.locked = false;
	}
	
	public void setLock(boolean lock) {
		this.locked = lock;
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	
	public void displayDice() {
		if (this.locked)
			System.out.println("This dice is locked with value: " + this.getValue());
		else
			System.out.println("This dice is unlocked with value: " + this.getValue());
	}
}


