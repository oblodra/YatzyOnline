package com.yatzy.dbtools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DBTools {
	
	
	//mysql
	//private static final String URL = "jdbc:mysql://localhost:3306/yatzy?useSSL=false";
	
	//ms sql
	private static final String URL = "jdbc:sqlserver://172.16.0.203:1433;DatabaseName=test_yatzy";
	
	private static Connection connection;
	
	public DBTools() {
		
	}
	
	
//--------Connect/disconnect database	
	
	
	public static void Test() {
		System.out.println("Testing!!!");
	}
	
	public static boolean Connect() {
		try {
			
			//mysql
			//Class.forName("com.mysql.jdbc.Driver");
			
			//ms sql
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			
			//mysql
			//connection = DriverManager.getConnection(URL, "root", "TR54jk14sql");
			
			//ms sql
			connection = DriverManager.getConnection(URL, "sa", "JacksSon5");
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
	}

	public static boolean Disconnect() {
		try {
			connection.close();
			return true;
		} catch (Exception ex) {
			return false;

		}
	}

	
//-----------QUERIES	
	
	
	// Använd detta om din query inte hämtar data från databasen
	// den kommer att returnera ett tal som motsvarar hur många rader som har
	// ändrats i DB'n
	// INSERT INT,DELETE, UPPDATE
	public static int ExecuteNonQuery(PreparedStatement prepareStatement) {
		try {
			return prepareStatement.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception when executing NonQuery");
			return -1;
		}
	}

	// Använd detta om du ska hämta data tabeller med SELECT satser.
	public static ResultSet ExecuteQuery(PreparedStatement sql) {
		try {
			return sql.executeQuery();

		} catch (SQLException ex) {
			System.out.println("Exception when executing Query");
			ex.printStackTrace();
			return null;

		}

	}

//-----------TRANSACTIONS	
	
	public static void StartTransaction() {	
		try {
			connection.setAutoCommit(false);
			String sql = "BEGIN TRANSACTION";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
		} 	
		catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	
	public static void RollbackTransaction() {	
		try {
			
			String sql = "ROLLBACK TRANSACTION";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
			connection.setAutoCommit(true);
			
			//if an error is found aborting program so new data will be added after transaction have stoped
			Disconnect();
			System.exit(0);
		} 	
		catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	
	
	public static void EndTransaction() {	
		try {
			
			String sql = "COMMIT TRANSACTION";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
			connection.setAutoCommit(true);
		} 	
		catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	
//------------STANDARD QUERIES	
	
	public static int ReadID() {
		try {	
			String sql = "SELECT @@IDENTITY";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			//ResultSet result = DBTools.ExecuteQuery(statement);
			DBTools.ExecuteNonQuery(statement);
			//System.out.println("Index generated: " + result);
			ResultSet rs = statement.getResultSet();
			int result = -1;
			if (rs!= null) {
				rs.next();
				result = rs.getInt(1);
				System.out.println("Identityindex: " + result);
			}
			return result;
		} 	
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
		return -1;
	}

	public static int createGameResultID(int gameID, int playerID, int score) {	
		try {
			String sql = "INSERT INTO [gameresult] ([Game_ID],[Player_ID], [Score]) VALUES(?,?,?)";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, gameID); 
			statement.setInt(2, playerID);
			statement.setInt(3, score);
			DBTools.ExecuteNonQuery(statement);
			return ReadID();
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
		return -1;
	}
	
	public static int createTurnID(int gameResultID, int turnNumber, String scoreType, int score) {	
		try {
			String sql = "INSERT INTO [turn] ([GameResult_ID],[TurnNumber], [ScoreType], [PointsEndOfTurn]) VALUES(?,?,?,?)";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, gameResultID); 
			statement.setInt(2, turnNumber);
			statement.setString(3, scoreType);
			statement.setInt(4, score);
			DBTools.ExecuteNonQuery(statement);
			return ReadID();
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
		return -1;
	}
	
	public static void createDiceResult(int turnID, int diceValue) {	
		try {
			String sql = "INSERT INTO [diceresult] ([Turn_ID],[DiceValue]) VALUES(?,?)";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, turnID); 
			statement.setInt(2, diceValue);
			DBTools.ExecuteNonQuery(statement);
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
	}
	
	public static int createGameID() {	
		try {
			String sql = "INSERT INTO [gamedata] ([GameTimeEnd]) VALUES(?)";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setString(1, "2000-01-01"); // default
			DBTools.ExecuteNonQuery(statement);
			return ReadID();
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
		return -1;
	}
	
	//Check if a name is in the database and return the PlayerID, 
	//if its not in the database it is added and the new PlayerID is returned
	public static int getPlayerID(String firstName, String lastName) {
		try {
			String sql = "SELECT [PlayerID] FROM [players] WHERE [FirstName]=? AND [LastName] = ?";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			
			ResultSet result = DBTools.ExecuteQuery(statement);
			if (result.next()) {
				int i = result.getInt(1);
				System.out.println("Found index " + i);
				return i;
			}
			else {
				sql = "INSERT INTO [players] ([FirstName], [LastName]) VALUES(?, ?)";
				statement = DBTools.connection.prepareStatement(sql);
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				
				DBTools.ExecuteNonQuery(statement);
				return ReadID();
			}	
		} 
		catch (Exception e) {
			e.printStackTrace();
			RollbackTransaction();
		}
		return -1;
	}
	
	public static void updateGameResultID(int gameResultID, int score) {
		try {
			String sql = "UPDATE [gameresult] SET score = ? WHERE [GameResultID] = ?";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, score); 
			statement.setInt(2, gameResultID);
			DBTools.ExecuteNonQuery(statement);
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
	}
	
	public static void updateGameData(int gameID) {
		
//		java.util.Date now = new java.util.Date();
//		java.sql.Date date = new java.sql.Date(now.getTime());
		try {
			
			/*
			String sql = "SELECT GETDATE()";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			//ResultSet result = DBTools.ExecuteQuery(statement);
			DBTools.ExecuteNonQuery(statement);
			//System.out.println("Index generated: " + result);
			ResultSet rs = statement.getResultSet();
			
			//System.out.println(LocalDateTime.now());
			
			if (rs!= null) {
				rs.next();
				date = rs.getDate(1);
			}
			
			
			*/
			String sql = "UPDATE [gamedata] SET GameTimeEnd = (SELECT GETDATE()) WHERE [GameID] = ?";		
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			//statement.setDate(1, date); 
			statement.setInt(1, gameID);
			DBTools.ExecuteNonQuery(statement);
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
			RollbackTransaction();
		}
	}
	
	public static String getPlayerName(int playerID) {
		try {
			String sql = "SELECT [FirstName], [LastName] FROM [players] WHERE [PlayerID]=?";

			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, playerID);

			ResultSet result = DBTools.ExecuteQuery(statement);
			
			if (result.next()) {
				return result.getString("FirstName").trim() + " " + result.getString("LastName").trim();
			}
			else
				return "";
			
		} catch (SQLException e) {
			e.printStackTrace();
			RollbackTransaction();
		}
		return "";
	}
	
//---------------SPECIAL QUERIES
	
	public static void showHighScore() {
		try {

			String sql = "SELECT pl.[FirstName], pl.[LastName], gr2.[MaxScore], gr.[Game_ID] "
					   + "FROM [gameresult] as gr "
					   + "INNER JOIN "
					   + "( SELECT [Game_ID], MAX([Score]) AS MaxScore FROM [gameresult] GROUP BY [Game_ID]) gr2 "
					   + "ON gr.[Game_ID] = gr2.[Game_ID] "
					   + "INNER JOIN "
					   + "[players] AS pl "
					   + "ON gr.[Player_ID] = pl.[PlayerID] "
					   + "WHERE gr2.MaxScore = gr.[Score] "
					   + "ORDER BY MaxScore DESC ";
			
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
			
			System.out.println("HighScoreList from the Yatzy database");
			System.out.println("------------------------");
			int counter = 1;
			if (result != null)
				System.out.println("Name       Score   GameID");
				while (result.next()) {
					System.out.println(counter  + "\t" 
									+ result.getString("FirstName") + "\t"
									+ result.getString("LastName")  + "\t"
									+ result.getString("MaxScore")  + "\t" 
									+ result.getString("Game_ID")
									);
					counter++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void showPlayerHistory(int input) {
		try {
			
//			System.out.println("Enter PlayerID: ");
//			Scanner scanIn = new Scanner(System.in);
//			int input = scanIn.nextInt();
//			scanIn.close();
			
			System.out.println("Player History for " + getPlayerName(input));
			System.out.println("------------------------");
			
			String sql = "WITH maxPrice AS "
					+"( "
					+"SELECT [Game_ID], [Player_ID], [Score], "
					+"ROW_NUMBER() OVER (Partition BY [Game_ID] ORDER BY [Score] DESC) rn "
					+"FROM [gameresult] "
					+") "
					+", minPrice AS "
					+"( "
					+"SELECT [Game_ID], [Player_ID], [Score], "
					+"ROW_NUMBER() OVER (Partition BY [Game_ID] ORDER BY [Score] ASC) rn "
					+"FROM [gameresult] "  
					+") "
					+"SELECT a.[Game_ID], " 
					+"pl1.[FirstName] AS WinFirstName, "
					+"pl1.[LastName] AS WinLastName, " 
					+"b.[Score] MaxScore, " 
					+"pl2.[FirstName] AS LosFirstname, "
					+"pl2.[LastName] AS LosLastName, " 
					+"c.[Score] MinScore, "
					+"gd.[GameTimeEnded] "
					+"FROM    (SELECT DISTINCT [Game_ID] FROM [gameresult]) a "
					+"INNER JOIN maxPrice b "
					+"ON a.[Game_ID] = b.[Game_ID] AND b.rn = 1 "
					+"INNER JOIN minPrice c "
					+"ON a.[Game_ID] = c.[Game_ID] AND c.rn = 1 "
					+"INNER JOIN " 
					+"( SELECT [Game_ID] FROM [gameresult] WHERE [Player_ID] = ? GROUP BY [Game_ID]) d "
					+"ON a.[Game_ID] = d.[Game_ID] "
					+"INNER JOIN [players] pl1 "
					+"ON b.[Player_ID] = pl1.[PlayerID] "	 
					+"INNER JOIN [players] pl2 "
					+"ON c.[Player_ID] = pl2.[PlayerID] "
					+"INNER JOIN [gamedata] gd "
					+"ON a.[Game_ID] = gd.[GameID]";
		  	
		  
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, input);
			
			ResultSet result = DBTools.ExecuteQuery(statement);
			
			int counter = 1;
			if (result != null)
				System.out.println("GameID Winner  Score   Loser  Score  EndTime");
				
				while (result.next()) {
					System.out.println(counter  + "\t"
						+ result.getString("Game_ID")    + "\t" 
						+ result.getString("WinFirstName") + "\t"
						+ result.getString("WinLastName")  + "\t"
						+ result.getString("MaxScore")  + "\t"
						+ result.getString("LosFirstName") + "\t"
						+ result.getString("LosLastName")  + "\t"
						+ result.getString("MinScore")  + "\t"
						+ result.getString("GameTimeEnded"));
					counter++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void showAllTurnsForAGame(int gameID) {
		try {

			String sql = " SELECT tu.TurnNumber, "
					+ "pl.FirstName, "
					+ "pl.LastName, "
					+ "dr.DiceValue, "
					+ "tu.ScoreType, "
					+ "tu.PointsEndOfTurn "
					
					+ "FROM [turn] AS tu "
					+ "INNER JOIN "
					+ "[gameresult] AS gr "
					+ "ON tu.GameResult_ID = gr.GameResultID "
					
					+ "INNER JOIN "
					+ "[players] AS pl "
					+ "ON gr.Player_ID = pl.PlayerID "
					
					+ "INNER JOIN "
					+ "[diceresult] as dr "
					+ "ON tu.TurnID = dr.Turn_ID "
					+ "WHERE gr.Game_ID = ?" ;
					
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			statement.setInt(1, gameID);
			ResultSet result = DBTools.ExecuteQuery(statement);
			
			System.out.println("Turn List for game with ID: " + gameID);
			System.out.println("------------------------");
			int counter = 1;
			int tempStore[] = new int[5];
			if (result != null)
				
				System.out.println("TurnNumber PlayerName  DiceResult  ScoreType  PointsEndOfTurn");
				while (result.next()) {
					for (int i= 0; i<5; i++) {
						tempStore[i] = result.getInt("DiceValue");
						if (i < 4)
							result.next();
					}
					
					System.out.print(result.getString("TurnNumber")  + "\t" 
									+ result.getString("FirstName") + "\t"
									+ result.getString("LastName")  + "\t");
					
					for (int j = 0; j<5; j++)				
						System.out.print(tempStore[j] + " ");			
															
					System.out.println("\t" + result.getString("ScoreType")  + "\t" 
									+ result.getString("PointsEndOfTurn")
									);
					counter++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//---------------PRINT RESULTS
	
	
	public static void showGames() {
		try {
			String sql = "SELECT pl.[FirstName], pl.[LastName], gr.[Game_ID] "
					   + "FROM [gameresult] as gr "
					   + "INNER JOIN "
					   + "[players] AS pl "
					   + "ON gr.[Player_ID] = pl.[PlayerID] "
					   + "ORDER BY gr.[Game_ID]";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
			
			System.out.println("GameList from the Yatzy database");
			System.out.println("------------------------");
			int counter = 1;
			if (result != null)
				System.out.println("Name   GameID");
				while (result.next()) {
					System.out.println(counter  + "\t" 
									+ result.getString("FirstName") + "\t"
									+ result.getString("LastName")  + "\t" 
									+ result.getString("Game_ID")
									);
					counter++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public static void showPlayers() {
		try {
			String sql = "SELECT [FirstName], [LastName], [PlayerID] "
					   + "FROM [players] "
					   + "ORDER BY [PlayerID]";
			PreparedStatement statement = DBTools.connection.prepareStatement(sql);
			ResultSet result = DBTools.ExecuteQuery(statement);
			
			System.out.println("PlayerList from the Yatzy database");
			System.out.println("------------------------");
			int counter = 1;
			if (result != null)
				System.out.println("PlayerID    Name");
				while (result.next()) {
					System.out.println(counter  + "\t" 
									+ result.getString("PlayerID")  + "\t"
									+ result.getString("FirstName") + "\t"
									+ result.getString("LastName") 
									);
					counter++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void PrintPlayerHistory(ResultSet result) {
		try {
			System.out.println("Player History for xxxx");
			System.out.println("------------------------");

			
			while (result.next()) {

				System.out.println(result.getString(1)  + "\t"
						+ result.getString("GameID")    + "\t" 
						+ result.getString("FirstName") + "\t"
						+ result.getString("LastName")  + "\t"
						+ result.getString("maxScore")  + "\t"
						+ result.getString("FirstName") + "\t"
						+ result.getString("LastName")  + "\t"
						+ result.getString("minScore")  + "\t"
						+ result.getString("GameTimeEnded"));
	
			}
		} 
		catch (Exception e) {
			e.printStackTrace();

		}

	}	
	
	

	
}
