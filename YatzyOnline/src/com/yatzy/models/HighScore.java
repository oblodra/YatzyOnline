package com.yatzy.models;

public class HighScore{
		private String FirstName;
		private String LastName;
		private int Score;
		private int GameID;

		public HighScore(){}
		
		public HighScore(String firstName, String lastName, int score, int gameid) {
			this.setFirstName(firstName);
			this.setLastName(lastName);
			this.setScore(score);
			this.setGameID(gameid);
		}

		public int getScore() {
			return Score;
		}

		public void setScore(int score) {
			Score = score;
		}
		
		public int getGameID() {
			return GameID;
		}

		public void setGameID(int gameid) {
			GameID = gameid;
		}
		
		public String getFirstName() {
			return FirstName;
		}

		public void setFirstName(String firstName) {
			FirstName = firstName;
		}
		
		public String getLastName() {
			return LastName;
		}

		public void setLastName(String lastName) {
			LastName = lastName;
		}
}


