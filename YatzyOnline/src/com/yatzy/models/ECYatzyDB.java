package com.yatzy.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ECYatzyDB {
	private static String connectionString = "jdbc:sqlserver://172.16.0.203:1433;DatabaseName=test_yatzy";

	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionString, "sa", "JacksSon5");
			return connection;
		} catch (Exception ex) {
			return null;
		}
	}

	public static boolean Disconnect(Connection connection) {
		try {
			connection.close();
			return true;
		} 
		catch (Exception ex) {
			return false;
		}
	}

	public static int executeNonQuery(PreparedStatement prepareStatement) {
		try {
			return prepareStatement.executeUpdate();
		} 
		catch (SQLException ex) {
			return -1;
		}
	}

	public static ResultSet executeQuery(PreparedStatement sql) {
		try {
			return sql.executeQuery();
		} 
		catch (SQLException ex) {
			return null;
		}
	}

	public static List<Player> getAllPlayers() {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				throw new Exception("Could not connect");
			}

			String sql = "SELECT [PlayerID], [FirstName], [LastName]  "
					   + "FROM [players] "
					   + "ORDER BY [PlayerID]";
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = executeQuery(statement);
			// Disconnect(connection);

			List<Player> resultList = new ArrayList<Player>();
			while (resultSet.next()) {
				Player player = new Player(resultSet.getInt("PlayerID"), resultSet.getString("FirstName"),
						resultSet.getString("LastName"));
				resultList.add(player);
			}
			return resultList;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			Disconnect(connection);
		}
	}
	
	public static Player getPlayerById(int id) {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				throw new Exception("Could not connect");
			}

			String sql = "SELECT [PlayerID], [FirstName], [LastName] FROM [players] WHERE [PlayerID]=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = executeQuery(statement);

			if (resultSet.next()) {
				Player player = new Player(resultSet.getInt("PlayerID"), resultSet.getString("FirstName"),
						resultSet.getString("LastName"));
				return player;
			}
			return null;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			Disconnect(connection);
		}
	}
	
	public static List<HighScore>  getHighScores() {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				throw new Exception("Could not connect");
			}
			
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
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = executeQuery(statement);
			
			List<HighScore> resultList = new ArrayList<HighScore>();
			while (resultSet.next()) {
				HighScore highscore = new HighScore(resultSet.getString("FirstName"),
						resultSet.getString("LastName"), resultSet.getInt("MaxScore"), resultSet.getInt("Game_ID"));
				
				resultList.add(highscore);
			}
			return resultList;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			Disconnect(connection);
		}
		
	}

	public static List<GameHistory> getPlayerHistoryById(int id) {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				throw new Exception("Could not connect");
			}
			
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
					+"gd.[GameTimeEnd] "
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
		  	
		  
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = executeQuery(statement);
			
			List<GameHistory> resultList = new ArrayList<GameHistory>();
			while (resultSet.next()) {
				GameHistory gameHist = new GameHistory( resultSet.getInt("Game_ID"),
														resultSet.getString("WinFirstName"),
														resultSet.getString("WinLastName"), 
														resultSet.getInt("MaxScore"), 
														resultSet.getString("LosFirstname"),
														resultSet.getString("LosLastName"), 
														resultSet.getInt("MinScore"), 
														resultSet.getDate("GameTimeEnd"));
				
				resultList.add(gameHist);
			}
			return resultList;
	
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			Disconnect(connection);
		}
		
	}
	
	public static List<GameTurn> getGameDataByGameId(int gameID) {
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				throw new Exception("Could not connect");
			}

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
					
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, gameID);
			ResultSet resultSet = executeQuery(statement);
			
			
			
			List<GameTurn> resultList = new ArrayList<GameTurn>();
			int tempStore[] = new int[5];
			while (resultSet.next()) {
				for (int i= 0; i<5; i++) {
					tempStore[i] = resultSet.getInt("DiceValue");
					if (i < 4)
						resultSet.next();
				}
				
				GameTurn gameturn = new GameTurn( resultSet.getInt("TurnNumber"),
														resultSet.getString("FirstName"),
														resultSet.getString("LastName"), 
														tempStore[0],
														tempStore[1],
														tempStore[2],
														tempStore[3],
														tempStore[4],
														resultSet.getString("ScoreType"),
														resultSet.getString("PointsEndOfTurn")); 
				
				System.out.println("Scoretype: " + resultSet.getString("ScoreType"));
				resultList.add(gameturn);
			}
			return resultList;
						
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			Disconnect(connection);
		}
		
	}
	
}
