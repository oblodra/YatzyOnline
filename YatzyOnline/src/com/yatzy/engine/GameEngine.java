package com.yatzy.engine;

import com.yatzy.rules.*;
import com.yatzy.helpclasses.*;
import com.yatzy.dice.Dice;
import com.yatzy.player.*;
import com.yatzy.dbtools.*;

public class GameEngine {
	
	private Player[] players;
	private int nbrOfPlayers;
	private GameRules gameRules;
	private Dice[] dices;
	
	private int[] roll;
	private LinkList[] theResultList; 
	Link[] bestResList;
	
	//variables for webgame
	private int webTurnNumber;
	private int webNbrRolls;
	private int activePlayer;
	private int[][] webData; //first row -> available button to save result, second row ->result
	private boolean enableSaveLoc;
	private boolean enableButton;
	private boolean enableLockDice;
	private boolean gameDone;
	private int[] gameOverData;
	private int winnerID;
	/*
	 * ButtonID for playYatzy
	 * 	0 : Roll Dice
	 *  1 : Continue
	 */	
	private int buttonID;
	
	//variables for db
	private DBTools dbTool;
	private int gameID;
	private int turnID;
	private int[][] playerIDs;
	
	
	public GameEngine(Player[] play, GameRules rules, int nbrOfPlayers) {
		
		this.players = play;
		this.nbrOfPlayers = nbrOfPlayers;
		this.gameRules = rules;
		this.dices = new Dice[this.gameRules.getNbrOfDices()];
		
		for (int j=0; j<this.gameRules.getNbrOfDices(); j++)
			dices[j] = new Dice();
		
		this.roll = new int[6];
		this.theResultList = new LinkList[15];
		
		for (int j=0; j<15; j++)
			this.theResultList[j] = new LinkList();
		
		this.bestResList = new Link[80];
		this.dbTool = new DBTools();
		
		//web
		webData = new int[3][17]; //index 16 bonus points from upper part and index 17 total sum
		//score each player, id of winner
		gameOverData = new int[this.nbrOfPlayers+1];
		
		//db
		this.playerIDs = new int[this.nbrOfPlayers][3]; //to save playerID and GameResultID for db
		for (int i=0; i<this.nbrOfPlayers; i++)
			for (int j=0; j<3; j++)
				this.playerIDs[i][j] = 0;	
		
		System.out.println(this.players[0].getFirstName());
		System.out.println(this.players[1].getFirstName());
		
		System.out.println(this.gameRules.getSaveLocThisTurnById(1, 5));
				
	}
	
	public void start() {
		
	}
	
	public void rollDices() {
		for (int d= 0; d < 5; d++) {
			dices[d].reRoll();
		}
	}
	
	public void resetDices() {
		for (int d= 0; d < 5; d++) {
			dices[d].resetDice();
		}
	}
	
	public void unlockDices() {
		for (int d= 0; d < 5; d++) {
			dices[d].unlockDice();
		}
	}
	
	//Transfer dice values to an array with the frequency of the dicerolls, 1,2,2,4,5 -> 1,2,0,1,1,0
	private void reWriteArray() {
		
		for (int j = 0; j<6; j++)
			this.roll[j] = 0;
		
		for (int i = 0; i<5; i++)
			this.roll[this.dices[i].getValue()-1 ]+=1;
	}
	
	private void makeATurn(Player player, int turn, int sortAfter) {
		
		System.out.println("\nRoll dices!");
		rollDices(); // roll dices
		
		for (int k = 0; k<5; k++)
			System.out.println("Roll " + k + " with value " + dices[k].getValue());
		
		reWriteArray(); // resets array and then fills it with data of the dice result
	
		compareToMatrix(player, turn, sortAfter);
		
	}
	
	
	private void evaluateResult(int saveIndex, int playerID) {
		int[] res = new int[6];
		int matchres;
		int currpoint;
		int[][] scoreMatrix = gameRules.getScoreMatrix();
		ArrayLinkMergesort sortAlg;
		int counter = 0;

		for (int l= 0; l<30; l++)
			this.bestResList[l] = null;
		
		for (int i = 0; i<scoreMatrix.length; i++) {
			matchres = 0;
			
			if ((scoreMatrix[i][0]-1) == saveIndex ) {
				/*
				for (int j=0; j<6; j++) {
					if (scoreMatrix[i][j+1] >= roll[j] && scoreMatrix[i][j+1] != 0) {
						res[j] = roll[j];
						matchres += roll[j];
					}
					else {
						res[j] = scoreMatrix[i][j+1];
					}		
				}
				*/
				for (int j=0; j<6; j++) {
					if (roll[j] <= scoreMatrix[i][j+1] && scoreMatrix[i][j+1] != 0) {
						res[j] = roll[j];
						matchres += roll[j];
					}
					else if (roll[j] > scoreMatrix[i][j+1] && scoreMatrix[i][j+1] != 0) {
						res[j] = scoreMatrix[i][j+1];
						matchres += scoreMatrix[i][j+1];					
					}
					else {
						res[j] = scoreMatrix[i][j+1];
					}		
				}
				
				
				// lägger bara till resultat om det finns minst en matchning
				
				currpoint = 0;
				if (matchres >= scoreMatrix[i][7])
					if (scoreMatrix[i][8] > 0) {
						currpoint = scoreMatrix[i][8];
					}
					else {
						currpoint = sum(res, 0 ,6);
					}
					
				else {
					currpoint = 0;
				}
					
				bestResList[counter++] = new Link(res.clone(), 
												  matchres, 
												  (scoreMatrix[i][8] > 0 ? scoreMatrix[i][8] : sum(scoreMatrix[i], 1, 6)), 
												  currpoint, 
												  saveIndex);					
								

				
			}
		}
		
		sortAlg = new ArrayLinkMergesort(bestResList, counter, 2);
		
		sortAlg.sort();
		
	}
	
	
	private void compareToMatrix(Player player, int turn, int sortAfter) {
		
		int[] res = new int[6];
		int matchres;
		int currpoint;
		int[][] tempScore = gameRules.getScoreMatrix();
		int[] scoreAvailable = player.getScoreSetLeft();
		ArrayLinkMergesort sortAlg;
		int avoidCrashTest = 0;
		
		//System.out.println(tempScore.length);
		
		//clearing all previous data lists
		for (int k = 0; k<15; k++)
			this.theResultList[k].clearList();
		for (int l= 0; l<80; l++)
			this.bestResList[l] = null;
		/*for (int n = 0; n<15; n++) {
			System.out.println(scoreAvailable[n]);
		}*/
		
		//System.out.println("Array content");
		for (int i = 0; i<tempScore.length; i++) {
			
			//System.out.println(scoreAvailable[tempScore[i][0]-1]);
			
			//Om reglerna tillåter att spara på en viss plats och spelaren inte använt det sparstället så kan man räkna ut tärningsresultatet 
			//för den här positionen och lägga till den till resultatlistan theResultList
			if (((this.gameRules.getSaveLocThisTurnById(this.webTurnNumber-1, (tempScore[i][0]-1))) == 1) &&
				(this.players[this.activePlayer].getSaveLocAvailable(tempScore[i][0]-1) == 1)) {
			//if (scoreAvailable[tempScore[i][0]-1] > 0 ) {
			//System.out.println("Line " + i);
				
				matchres = 0;
				
				for (int j=0; j<6; j++) {
					if (roll[j] <= tempScore[i][j+1] && tempScore[i][j+1] != 0) {
						res[j] = roll[j];
						matchres += roll[j];
					}
					else if (roll[j] > tempScore[i][j+1] && tempScore[i][j+1] != 0) {
						res[j] = tempScore[i][j+1];
						matchres += tempScore[i][j+1];					
					}
					else {
						res[j] = tempScore[i][j+1];
					}		
				}
				
			//	if(matchres > 0) {
					currpoint = 0;
					//kollar att antal tärningar som matchar scoreMatrix är lika med minimum som krävs att det ger poäng, dvs 4 5:or för 4tal
					if (matchres >= tempScore[i][7])
						//specialfall om man man slår yatzy så får man 50 poäng istället för att summera tärningarna
						if (tempScore[i][8] > 0) {
							//plockar fixvärde från scorematrix
							currpoint = tempScore[i][8];
						}
						else {
							//summerar tärningarna som ger poäng
							currpoint = sum(res, 0 ,6);
						}
					
					else {
						//ingen match som ger poäng sätts till 0
						currpoint = 0;
					}
					//lägger in tärningsresultatet i en linklist med id:t på var resultatet kan sparas			
					theResultList[tempScore[i][0]-1].insertFirst(res.clone(), matchres, (tempScore[i][8] > 0 ? tempScore[i][8] : sum(tempScore[i], 1, 6)), currpoint, tempScore[i][0]-1);			
						
					/*for (int k=0; k<6; k++) {
						System.out.print(res[k]);
					}
					System.out.print(" " + matchres + " " + sum(tempScore[i], 1, 6) + " " + sum(res, 0, 6));
					System.out.println();
					*/
				//}
			}
		}
		System.out.println();
		int counter = 0;
		int setSizeToSort = 5;
		int nbrToAdd;
		
		//går igenom varje linklist i thResultList och kan sortera dem på tre olika sätt, dvs baserat på 3 olika variabler i LinkList
		//därefter plockas upptill de 5 bästa resultaten från varje LinkList och läggs i bestResList 
		for (int i = 0; i<15; i++) {
			if (!theResultList[i].isEmpty()) {
				//System.out.println("Index: " + i);
				//theResultList[i].displayList();
				
				if (sortAfter == 0)
					theResultList[i].sortBestMatch(setSizeToSort);
				else if (sortAfter == 1)
					theResultList[i].sortBestMaxScore(setSizeToSort);
				else
					theResultList[i].sortBestCurrentScore(setSizeToSort);
				
				//theResultList[i].displayList();
				
				nbrToAdd = theResultList[i].getNbrOfLinks();
				if (nbrToAdd > 5)
					nbrToAdd = 5;
				
				if (nbrToAdd != 0) {
					for (int l= 0; l<nbrToAdd; l++) {
						bestResList[counter++] = theResultList[i].deleteFirst();
					}
				}				
			}
		}
		/*
		System.out.println("\nResultlist");
		for (int m = 0; m<counter; m++) {
			bestResList[m].display();
		}
		*/
		
		//skapar ett object av bestResList och sorterar resultatet med högsta värdet först		
		sortAlg = new ArrayLinkMergesort(bestResList, counter, sortAfter);
		
		sortAlg.sort();
		/*
		System.out.println("\nResultlist sorted");
		for (int m = 0; m<counter; m++) {
			bestResList[m].display();
		}
		*/
	}
	
	
	private void lockDice(int[] lockDiceArray) {
		int val = 0;
		for (int i = 0; i<5; i++) {
			val = dices[i].getValue();
			dices[i].unlockDice(); //not sure if a previous dice have been locked!
			if (lockDiceArray[val-1] > 0) {
				lockDiceArray[val-1]--;
				dices[i].lockDice();
			}
		}
	}
	
	private int sum(int[] resRoll, int startIndex, int nbrOfNbr) {
		int sum = 0;
		int counter = 0;
		// the array I want to calculate sum from starts at different indexes
		for (int i = startIndex; i<startIndex+nbrOfNbr; i++) {
			sum += resRoll[i] * (counter+1);
			counter++;
		}
		return sum;
	}
		
	private void startUp() {
		for (int i = 0; i<15; i++) {
			this.theResultList[i] = new LinkList();
		}
		for (int j = 0; j<5; j++) {
			this.dices[j] = new Dice();
		}
	}
	
	public void startNewWebGame() {
				
	/*	
	 * 	Starting up the database to save result! DB
	 * 
		if (dbTool.Connect()) {
			System.out.println("Connected!!");
		}
		
		dbTool.StartTransaction();
		
		this.gameID = dbTool.createGameID();
		
		System.out.println("GameID: " + this.gameID);
		
		for (int i=0; i < this.nbrOfPlayers; i++) {
			// get playID from database for each player
			this.playerIDs[i][0] = dbTool.getPlayerID(players[i].getFirstName(), players[i].getLastName());
			// create GameResultID for each player
			this.playerIDs[i][1] = dbTool.createGameResultID(this.gameID, this.playerIDs[i][0], 0);
			System.out.println("Player: " + players[i].getFirstName() + " " + players[i].getLastName() + " with " + this.playerIDs[i][0] + " " + this.playerIDs[i][1]);
		}
		
	*/	
				
		webTurnNumber = 1;
		webNbrRolls = 0;
		activePlayer = 0;
		gameDone = false;
		winnerID = -1;
		
		//if its a new round
		
		rollDices();
			
		for (int ld = 0; ld < 5; ld++)
			dices[ld].displayDice();
		
		//clearing webData
		for (int i=0; i<17; i++)
			for (int j = 0; j<2; j++)
				this.webData[j][i] = 0;
	}
	
	public void endWebGame() {
		int maxID = 0;
		int maxValue = 0;
		//add bonus points
		
		System.out.println("End game started!");
		
		for (int i= 0; i<this.nbrOfPlayers; i++) {
			this.playerIDs[i][2]+=checkBonus(i);
			this.gameOverData[i] = this.playerIDs[i][2];
			//find the maxpoints and its id
			if (this.gameOverData[i] > maxValue) {
				maxValue = this.gameOverData[i];
				maxID = i;
			}		
		}
		
		//last index is the id of the winner
		this.gameOverData[this.nbrOfPlayers] = maxID;
		this.gameDone = true;
		this.winnerID = maxID;
		
		for (int j=0; j<3; j++) 
			System.out.print("gameOverdata " + this.gameOverData[j] + " ");
		System.out.println();
	/*
	 *   End of transaction, DB
	 * 	
		
		for (int i=0; i < this.nbrOfPlayers; i++) {
			// update GameResult with final score
			dbTool.updateGameResultID(this.playerIDs[i][1], this.playerIDs[i][2]);
			// update GameData with the time the game ended
			dbTool.updateGameData(this.gameID);			
		}		
		
		dbTool.EndTransaction();
		
		System.out.println("\nEnd game!");
		
		dbTool.Disconnect();
		
	*/		
	}
	
	public int checkBonus(int id) {
		int sum = 0;
		
		//this should be in gamerules, but just making a simple method in engine
		//if the sum of the 6 first elements are above 62 you get 50 bonuspoints
		for (int i = 0; i<6; i++)
			sum+=players[id].getScoreBoardResult(i);
		
		if (sum > 62)
			sum=50;
		else
			sum=0;

		return sum;
	}
	
	public int totalSum(int id) {
		int sum = 0;
		for (int i = 0; i<15; i++)
			sum+=players[id].getScoreBoardResult(i);
		return sum;
	}
	//diceRolled, true om användare/dator slagit tärningar, vilket index om användare vill spara sitt resultat, lockDice, om true så är tärning låst 
	public void calcNextStepWebGame(boolean diceRolled, int saveIndex, boolean[] lockDice) {
		if (players[this.activePlayer] instanceof Computer) {
			System.out.println("Entering computer player!");
						
			//roll dices
			makeATurn(players[this.activePlayer], 4, 0);
			
			//after evaluating the roll, some dices may be locked
			lockDice(bestResList[0].getData());
			
			//display diceroll
			for (int ld = 0; ld < 5; ld++)
				dices[ld].displayDice();
			
			//roll unlocked dices
			makeATurn(players[this.activePlayer], 4, 0);
			
			//after evaluating the roll, some dices may be locked
			lockDice(bestResList[0].getData());
			
			//display diceroll
			for (int ld = 0; ld < 5; ld++)
				dices[ld].displayDice();
			
			//last roll of unlocked dices
			makeATurn(players[this.activePlayer], 4, 2);
			
			//display diceroll
			for (int ld = 0; ld < 5; ld++)
				dices[ld].displayDice();
			
			//update scoreboard
			players[this.activePlayer].updateScoreBoard(bestResList[0].getIndex(), bestResList[0].getCurrentPoints());
			
			//updates total score
			this.playerIDs[this.activePlayer][2] += bestResList[0].getCurrentPoints();
			
			//display current result in console
			players[this.activePlayer].displayScoreBoard();
			
			//update database
			/*
			 * DB
			 * 
			//update database
			int turnID = dbTool.createTurnID(this.playerIDs[this.activePlayer][1], 
											 this.webTurnNumber, 
											 rules.lookupScoreType(bestResList[0].getIndex()-1), 
											 bestResList[0].getCurrentPoints());
					
			for (int di = 0; di < 5; di++)
				dbTool.createDiceResult(turnID, this.dices[di].getValue());
			*/
			this.unlockDices();
			
			// the alternatives are that both players are done with the the turn and turn ends 
			//or just one player is done and next takes over
			if ((this.activePlayer+1) == this.nbrOfPlayers) {
				this.webTurnNumber++;
				if (this.webTurnNumber == 16) {
					endWebGame();
				}
				else
					this.activePlayer = 0;	
				
			}
			else {
				this.activePlayer++;
			}
			this.webNbrRolls = 0 ;
			System.out.println("Active player: " + this.activePlayer);
			
			for (int j=0; j<15; j++) {
				//checkes the orderofplay matrix in gamerules and the current scoreboard which locations it will be possible
				//for the player to save result in the current turn
				if (this.webTurnNumber < 16) {
					if (((this.gameRules.getSaveLocThisTurnById(this.webTurnNumber-1, j)) == 1)&&(this.players[this.activePlayer].getSaveLocAvailable(j) == 1)) 
						this.webData[0][j] = 1;
					else
						this.webData[0][j] = 0;	
					
				}
				this.webData[1][j] = this.players[0].getScoreBoardResult(j);
				this.webData[2][j] = this.players[1].getScoreBoardResult(j);
			}
			this.webData[1][15] = checkBonus(0);
			this.webData[2][15] = checkBonus(1);
			
			this.webData[1][16] = totalSum(0) + this.webData[1][15];
			this.webData[2][16] = totalSum(1) + this.webData[2][15];
			
			this.buttonID = 1;
			this.enableButton = true;
			this.enableLockDice = false;
			this.enableSaveLoc = false;
				
		}
		else if (players[this.activePlayer] instanceof Human) {
			if ((saveIndex > 0)) {
					//save result to saveIndex and go to next player
					System.out.println("Entering save result human!");
					//creates the bestResList
					evaluateResult(saveIndex-1, this.activePlayer );
					System.out.println("Maxpoints for that location: " + bestResList[0].getCurrentPoints());
					bestResList[0].display();
					
					players[this.activePlayer].updateScoreBoard(bestResList[0].getIndex(), bestResList[0].getCurrentPoints());
					
					//updates total score
					this.playerIDs[this.activePlayer][2] += bestResList[0].getCurrentPoints();
					
					//Test
					//display current result
					players[this.activePlayer].displayScoreBoard();
					
					/*
					 * DB
					 * 
					//update database
					int turnID = dbTool.createTurnID(this.playerIDs[this.activePlayer][1], 
													 this.webTurnNumber, 
													 rules.lookupScoreType(bestResList[0].getIndex()-1), 
													 bestResList[0].getCurrentPoints());
							
					for (int di = 0; di < 5; di++)
						dbTool.createDiceResult(turnID, this.dices[di].getValue());
					*/
					//reset dices and locks
					this.unlockDices();
					
					// the alternatives are that both players are done with the the turn and turn ends 
					//or just one player is done and next takes over
					if ((this.activePlayer+1) == this.nbrOfPlayers) {
						this.webTurnNumber++;
						System.out.println("webTurnNumber " + this.webTurnNumber);
						
						if (this.webTurnNumber == 16) {
							endWebGame();
						}
						else
							this.activePlayer = 0;	
						
					}
					else {
						this.activePlayer++;
					}
					this.webNbrRolls = 0 ;
					System.out.println("Active player: " + this.activePlayer);
					
					for (int j=0; j<15; j++) {
						//checks the orderofplay matrix in gamerules and the current scoreboard which locations it will be possible
						//for the player to save result in the current turn
						if (this.webTurnNumber < 16) {
							if (((this.gameRules.getSaveLocThisTurnById(this.webTurnNumber-1, j)) == 1)&&(this.players[this.activePlayer].getSaveLocAvailable(j) == 1)) 
								this.webData[0][j] = 1;
							else
								this.webData[0][j] = 0;	
							
						}
						this.webData[1][j] = this.players[0].getScoreBoardResult(j);
						this.webData[2][j] = this.players[1].getScoreBoardResult(j);
					}
					this.webData[1][15] = checkBonus(0);
					this.webData[2][15] = checkBonus(1);
					
					this.webData[1][16] = totalSum(0) + this.webData[1][15];
					this.webData[2][16] = totalSum(1) + this.webData[2][15];
					
					
					this.enableButton = true;
					this.enableLockDice = false;
					this.enableSaveLoc = false;
			}
		
		//dice is rolled and nbr of times dice is rolled for certain player and specific turn is less then 3
			else if (diceRolled && this.webNbrRolls < 3){
					
					System.out.println("Entering human roll dice!");
					this.webNbrRolls++;
					
					//lock dices if needed then roll the unlocked dices
					for (int j= 0; j<5; j++) {
						if (lockDice[j])
							dices[j].lockDice();
						else
							dices[j].unlockDice();
					}
					
					// roll dices
					rollDices(); 
					
					// put dice result into an array that is easier to use when evaluating result
					reWriteArray();
								
					//calculate what save locations are ok to save at according to gamerules
					
					for (int j=0; j<15; j++) {
						//checkes the orderofplay matrix in gamerules and the current scoreboard which locations it will be possible
						//for the player to save result in the current turn
						if (this.webTurnNumber < 16) {
							if ((this.gameRules.getSaveLocThisTurnById(this.webTurnNumber-1, j) == 1)&&(this.players[this.activePlayer].getSaveLocAvailable(j) == 1)) 
								this.webData[0][j] = 1;
							else
								this.webData[0][j] = 0;	
						}
						
						this.webData[1][j] = this.players[0].getScoreBoardResult(j);
						this.webData[2][j] = this.players[1].getScoreBoardResult(j);
					}
					
					this.buttonID = 0;
					this.enableButton = ((this.webNbrRolls < 3) ? true : false);
					this.enableLockDice = (((this.webNbrRolls > 0)) ? true : false);
					this.enableSaveLoc = (((this.webNbrRolls > 0)) ? true : false);
			}
		
		// special cases for first turn
			else if (!diceRolled && this.webNbrRolls == 0) {
			
					for (int j=0; j<15; j++) {
						this.webData[0][j] = 1;
						this.webData[1][j] = this.players[0].getScoreBoardResult(j);
						this.webData[2][j] = this.players[1].getScoreBoardResult(j);
					}
					
					this.buttonID = 0;
					this.enableButton = ((this.webNbrRolls < 3) ? true : false);
					this.enableLockDice = (((this.webNbrRolls > 0)) ? true : false);
					this.enableSaveLoc = (((this.webNbrRolls > 0)) ? true : false);
			}
			else {
				//shouldn't be able to get here
				System.out.println("Error!!!");
			}
		}	
	}
	
	public boolean gameDone() {
		return this.gameDone;
	}
	
	
	//obsolete?
	public int[] getEndGameScores() {
		return this.gameOverData;
	}
	
	public int getWinnerID() {
		return this.winnerID;
	}
	
	public int[][] getWebData() {
		return this.webData;
	}
	
	public String[] getPlayerNames() {
		String[] playerNames = new String[this.nbrOfPlayers];
		for (int i=0; i<this.nbrOfPlayers; i++) {
			playerNames[i] = players[i].getName();
		}
		return playerNames;
	}
	
	public int getActivePlayer() {
		return this.activePlayer;
	}
	
	public int getCurrentTurn() {
		return this.webTurnNumber;
	}
	
	public boolean[] getLockedDices() {
		boolean[] locks = new boolean[5]; 
		for (int i = 0; i<5; i++) {
			locks[i] = this.dices[i].isLocked();
		}			
		return locks;
	}
	
	public boolean[] getEnabledButtons() {
		boolean[] eb = {this.enableButton,this.enableLockDice,this.enableSaveLoc};
		return eb;
	}
	
	public int getButtonID() {
		return this.buttonID;
	}
	
	public int[] getDiceResult() {
		int[] dr = new int[5];
		for (int i= 0; i<5; i++) {
			dr[i] = dices[i].getValue();
		}
		
		return dr;
	}
		
	public void startTestRun() {

/*		
 * 		For running simulations on console, not for webGame!
 * 
 * 
*/		
		System.out.println("\nTestrun!");
		System.out.println("Antal tärningar: " + gameRules.getNbrOfDices());
		startUp();

		//dbTool.Test();
		
		if (dbTool.Connect()) {
			System.out.println("Connected!!");
		}
		
		dbTool.StartTransaction();
		
		this.gameID = dbTool.createGameID();
		
		System.out.println("GameID: " + this.gameID);
		
		for (int i=0; i < this.nbrOfPlayers; i++) {
			// get playID from database for each player
			this.playerIDs[i][0] = dbTool.getPlayerID(players[i].getFirstName(), players[i].getLastName());
			// create GameResultID for each player
			this.playerIDs[i][1] = dbTool.createGameResultID(this.gameID, this.playerIDs[i][0], 0);
			System.out.println("Player: " + players[i].getFirstName() + " " + players[i].getLastName() + " with " + this.playerIDs[i][0] + " " + this.playerIDs[i][1]);
		}
		
		
		
//		for (int i = 0; i< 3; i++) {
//			for (int j = 0 ; j< rules.getNbrOfDices(); j++) {
				//int i = 0;
				//System.out.println(rules.getNbrOfDices());
				
				

		//players[i].updateScoreBoard(11, 24); //set chans to 0

		for (int turnNumber = 0; turnNumber < gameRules.getNbrOfTurns(); turnNumber++) {
	
			for (int pl = 0; pl < this.nbrOfPlayers; pl++) {				
				if (players[pl] instanceof Human) {
					System.out.println("Human player!");
					//wait for user to click roll dices
					// then for player to lock/unlock dices
					//then choose to save result on a specific place or reroll dices
					// after maximum 3 rolls tun goes to next player					
				}
				else if (players[pl] instanceof Computer) {
					System.out.println("\nComputer player!");
					
					//remove result and possible lock of dices from previous turn
					resetDices(); 
					
					//roll dices
					makeATurn(players[pl], 4, 0);
					
					//after evaluating the roll, some dices may be locked
					lockDice(bestResList[0].getData());
					
					//display diceroll
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					//roll unlocked dices
					makeATurn(players[pl], 4, 0);
					
					//after evaluating the roll, some dices may be locked
					lockDice(bestResList[0].getData());
					
					//display diceroll
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					//last roll of unlocked dices
					makeATurn(players[pl], 4, 2);
					
					//display diceroll
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					//update scoreboard
					players[pl].updateScoreBoard(bestResList[0].getIndex()-1, bestResList[0].getCurrentPoints());
					this.playerIDs[pl][2] += bestResList[0].getCurrentPoints();
					
					//display current result
					players[pl].displayScoreBoard();
					
					//update database
					int turnID = dbTool.createTurnID(this.playerIDs[pl][1], 
													 turnNumber+1, 
													 gameRules.lookupScoreType(bestResList[0].getIndex()-1), 
													 bestResList[0].getCurrentPoints());
					
					
					for (int di = 0; di < 5; di++)
						dbTool.createDiceResult(turnID, this.dices[di].getValue());
					
				}
			}
		}
		
		for (int i=0; i < this.nbrOfPlayers; i++) {
			// update GameResult with final score
			dbTool.updateGameResultID(this.playerIDs[i][1], this.playerIDs[i][2]);
			// update GameData with the time the game ended
			dbTool.updateGameData(this.gameID);			
		}		
		
		dbTool.EndTransaction();
		
		System.out.println("\nEnd test turn!");
		
		dbTool.Disconnect();
		
		
		
/*					
					resetDices(); //remove result and possible lock of dices from previous turn
					
					makeATurn(players[i], 4, 0);
*/					
					/*
					System.out.println("Roll dices!");
					rollDices(); // roll dices
					for (int k = 0; k<5; k++)
						System.out.println("Roll " + k + " with value " + dices[k].getValue());
					//System.out.println("Roll -> array!");
					reWriteArray(); // resets array and then fills it with data of the dice result
					System.out.println("Evaluate roll!");
					compareToMatrix(players[i],4, 0);
					*/
/*					
					lockDice(bestResList[0].getData());
					
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					makeATurn(players[i], 4, 0);
*/					
					/*
					System.out.println("Roll dices!");
					rollDices(); // roll dices
					for (int k = 0; k<5; k++)
						System.out.println("Roll " + k + " with value " + dices[k].getValue());
					//System.out.println("Roll -> array!");
					reWriteArray(); // resets array and then fills it with data of the dice result
					System.out.println("Evaluate roll!");
					compareToMatrix(players[i],4, 0);
					*/
/*					
					lockDice(bestResList[0].getData());
					
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					makeATurn(players[i], 4, 2);
*/					
					/*
					rollDices(); // roll dices
					for (int k = 0; k<5; k++)
						System.out.println("Roll " + k + " with value " + dices[k].getValue());
					//System.out.println("Roll -> array!");
					reWriteArray(); // resets array and then fills it with data of the dice result
					System.out.println("Evaluate roll!");
					compareToMatrix(players[i],4, 2);
					*/
/*					
					for (int ld = 0; ld < 5; ld++)
						dices[ld].displayDice();
					
					players[i].updateScoreBoard(bestResList[0].getIndex()-1, bestResList[0].getCurrentPoints());
					players[i].displayScoreBoard();
*/				
		
				
//			}			
//		}
		
		
	}
}
