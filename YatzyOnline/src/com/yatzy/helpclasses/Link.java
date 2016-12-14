package com.yatzy.helpclasses;

public class Link {
	private int[] data;
	private int match;
	private int maxpoints;
	private int currentpoints;
	private int index;
	private Link next;
	
	public Link(int[] data, int match, int maxpoints, int currentpoints, int index) {
		this.data = data;
		this.match = match;
		this.maxpoints = maxpoints;
		this.currentpoints = currentpoints;
		this.index = index;
		
	}
	
	public void display() {
		System.out.print("Matchad matris: [ ");
		for (int i = 0; i<this.data.length; i++) {
			System.out.print(this.data[i] + " ");
		}
		System.out.println("] med index " + this.index +" med matchning: " + this.match + " för att få poäng " + this.maxpoints + " och har för tillfället " + this.currentpoints);
	}
	
	public Link getNext() {
		return this.next;
	}
	
	public void setNext(Link next) {
		this.next = next;
	}
	
	public int[] getData() {
		return this.data;
	}
	
	public void setData(int[] data) {
		this.data = data;
	}
	
	public int getMatch() {
		return this.match;
	}
	
	public void setMatch(int match) {
		this.match = match;
	}
	
	public int getMaxPoints() {
		return this.maxpoints;
	}
	
	public void setMaxPoints(int maxpoints) {
		this.maxpoints = maxpoints;
	}
	
	public int getCurrentPoints() {
		return this.currentpoints;
	}
	
	public void setPoints(int currentpoints) {
		this.currentpoints = currentpoints;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
	
}
