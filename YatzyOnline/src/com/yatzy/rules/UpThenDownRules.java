package com.yatzy.rules;

public class UpThenDownRules extends GameRules{
	
	int[][] upThenDownOrderOfPlay;
	
	int[][] dataMatrix 	=  {{1, 5, 0, 0, 0, 0, 0,0,0}, // ettor
							{2, 0, 5, 0, 0, 0, 0,0,0}, // tvåor
							{3, 0, 0, 5, 0, 0, 0,0,0}, // treor
							{4, 0, 0, 0, 5, 0, 0,0,0}, // fyror
							{5, 0, 0, 0, 0, 5, 0,0,0}, // femmor
							{6, 0, 0, 0, 0, 0, 5,0,0}, // sexor
							
							{7, 2, 2, 0, 0, 0, 0,4,0}, // två par
							{7, 2, 0, 2, 0, 0, 0,4,0},
							{7, 2, 0, 0, 2, 0, 0,4,0},
							{7, 2, 0, 0, 0, 2, 0,4,0},
							{7, 2, 0, 0, 0, 0, 2,4,0},
							{7, 0, 2, 2, 0, 0, 0,4,0},
							{7, 0, 2, 0, 2, 0, 0,4,0},
							{7, 0, 2, 0, 0, 2, 0,4,0},
							{7, 0, 2, 0, 0, 0, 2,4,0},
							{7, 0, 0, 2, 2, 0, 0,4,0},
							{7, 0, 0, 2, 0, 2, 0,4,0},
							{7, 0, 0, 2, 0, 0, 2,4,0},
							{7, 0, 0, 0, 2, 2, 0,4,0},
							{7, 0, 0, 0, 2, 0, 2,4,0},
							{7, 0, 0, 0, 0, 2, 2,4,0},
							
							{7, 2, 0, 0, 0, 0, 0,2,0}, // ett par
							{7, 0, 2, 0, 0, 0, 0,2,0},
							{7, 0, 0, 2, 0, 0, 0,2,0},
							{7, 0, 0, 0, 2, 0, 0,2,0},
							{7, 0, 0, 0, 0, 2, 0,2,0},
							{7, 0, 0, 0, 0, 0, 2,2,0},
							
							{8, 4, 0, 0, 0, 0, 0,4,0}, //tretal / fyrtal
							{8, 0, 4, 0, 0, 0, 0,4,0},
							{8, 0, 0, 4, 0, 0, 0,4,0},
							{8, 0, 0, 0, 4, 0, 0,4,0},
							{8, 0, 0, 0, 0, 4, 0,4,0},
							{8, 0, 0, 0, 0, 0, 4,4,0},
							{8, 3, 0, 0, 0, 0, 0,3,0},
							{8, 0, 3, 0, 0, 0, 0,3,0},
							{8, 0, 0, 3, 0, 0, 0,3,0},
							{8, 0, 0, 0, 3, 0, 0,3,0},
							{8, 0, 0, 0, 0, 3, 0,3,0},
							{8, 0, 0, 0, 0, 0, 3,3,0},
							
							{9, 1, 1, 1, 1, 1, 0,5,0}, // liten straight
							
							{10, 0, 1, 1, 1, 1, 1,5,0}, // stor straight
							
							{11, 2, 3, 0, 0, 0, 0,5,0}, //kåk
							{11, 2, 0, 3, 0, 0, 0,5,0},
							{11, 2, 0, 0, 3, 0, 0,5,0},
							{11, 2, 0, 0, 0, 3, 0,5,0},
							{11, 2, 0, 0, 0, 0, 3,5,0},
							
							{11, 3, 2, 0, 0, 0, 0,5,0},
							{11, 0, 2, 3, 0, 0, 0,5,0},
							{11, 0, 2, 0, 3, 0, 0,5,0},
							{11, 0, 2, 0, 0, 3, 0,5,0},
							{11, 0, 2, 0, 0, 0, 3,5,0},
							
							{11, 3, 0, 2, 0, 0, 0,5,0},
							{11, 0, 3, 2, 0, 0, 0,5,0},
							{11, 0, 0, 2, 3, 0, 0,5,0},
							{11, 0, 0, 2, 0, 3, 0,5,0},
							{11, 0, 0, 2, 0, 0, 3,5,0},
							
							{11, 3, 0, 0, 2, 0, 0,5,0},
							{11, 0, 3, 0, 2, 0, 0,5,0},
							{11, 0, 0, 3, 2, 0, 0,5,0},
							{11, 0, 0, 0, 2, 3, 0,5,0},
							{11, 0, 0, 0, 2, 0, 3,5,0},
							
							{11, 3, 0, 0, 0, 2, 0,5,0},
							{11, 0, 3, 0, 0, 2, 0,5,0},
							{11, 0, 0, 3, 0, 2, 0,5,0},
							{11, 0, 0, 0, 3, 2, 0,5,0},
							{11, 0, 0, 0, 0, 2, 0,5,0},
							{11, 0, 0, 0, 0, 2, 3,5,0},
							
							{11, 3, 0, 0, 0, 0, 2,5,0},
							{11, 0, 3, 0, 0, 0, 2,5,0},
							{11, 0, 0, 3, 0, 0, 2,5,0},
							{11, 0, 0, 0, 3, 0, 2,5,0},
							{11, 0, 0, 0, 0, 3, 2,5,0},
							
							{12, 5, 5, 5, 5, 5, 5,0,0}, // chans
							
							{13, 5, 0, 0, 0, 0, 0,5,50}, // yatzy
							{13, 0, 5, 0, 0, 0, 0,5,50},
							{13, 0, 0, 5, 0, 0, 0,5,50},
							{13, 0, 0, 0, 5, 0, 0,5,50},
							{13, 0, 0, 0, 0, 5, 0,5,50},
							{13, 0, 0, 0, 0, 0, 5,5,50}};
	
	String[] lookupScoreType = {"Ones","Twos","Threes","Fours","Fives","Sixes","One Pair","Two Pair","Three of a Kind","Four of a Kind","Small Straight","Large Straight","Full House","Chans","Yatzy"};
	
	public UpThenDownRules() {
		setNbrOfDices(5);
		setNbrOfTurns(15);
		upThenDownOrderOfPlay = new int[15][15];
		setOOP();
		setOrderOfPlay(upThenDownOrderOfPlay);
		setScoreMatrix(dataMatrix);
	}
	
	public void setOOP() {
		for (int i = 0; i<15; i++) {
			for (int j = 0; j < 15; j++) {
				upThenDownOrderOfPlay[i][j] = 0;
			}
		}
		for (int k=0; k<6; k++) {
			for (int l = 0; l<6; l++) {
				upThenDownOrderOfPlay[k][l] = 1;
			}
		}
		for (int m=6; m<15; m++) {
			for (int n = 6; n<15; n++) {
				upThenDownOrderOfPlay[m][n] = 1;
			}
		}
			
	}
	
	@Override
	public String lookupScoreType(int id) {
		return this.lookupScoreType[id];
	}
	
	/*
	@Override
	public int[][] getScoreMatrix() {
		return dataMatrix;
	}
	
	@Override
	public int[] getTurnRules(int turn) {
		return this.upThenDownOrderOfPlay[turn]; //need to be picked out of the orderOfPlayMatrix based on the indata turn
	}
	*/
	
}
