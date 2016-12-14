package com.yatzy.test;

import com.yatzy.rules.*;

public class TestApplication {
	public static void main(String[] args) {
		
		
		GameRules gamerules = new OptionalRules();
		
		GameRules gamerules2 = new UpThenDownRules();
		
		int[] ij;
		
		ij = new int[5];
		
		for (int k=0; k<5; k++) {
			ij[k] = 55;
		}
		
		System.out.println("Length of array" + ij.length);
		
		int turn = 1;
		
		for (int i=0; i<15; i++) {
			if (gamerules.getSaveLocThisTurnById(turn-1, i) == 1) {
				System.out.println("1A one found on index " + i);
			}
			if (gamerules2.getSaveLocThisTurnById(turn-1, i) == 1) {
				System.out.println("2A one found on index " + i);
			}
			
		}
			
		
		
		
	}
}

