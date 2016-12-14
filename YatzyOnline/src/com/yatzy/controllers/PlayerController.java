package com.yatzy.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yatzy.models.GameHistory;
import com.yatzy.models.Player;
import com.yatzy.models.HighScore;
import com.yatzy.models.GameTurn;
import com.yatzy.models.ECYatzyDB;

@WebServlet("/player")
public class PlayerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlayerController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve parameter 
		String action = request.getParameter("action");
		if (action == null) {
			defaultAction(request, response);
		} else {
			switch (action) {
			case "GetAllPlayers":
				System.out.println("PlayerController:GetAllPlayers");
				getAllPlayers(request, response);
				break;
			case "GetPlayerById":
				System.out.println("PlayerController:GetPlayerById");
				getPlayerById(request, response);
				break;
			case "GetHighScore":
				System.out.println("PlayerController:GetHighScore");
				getHighScore(request, response);
				break;	
			case "GetGameHistoryById":
				System.out.println("PlayerController:GetGameHistoryById");
				getGameHistoryById(request, response);
				break;
			case "GetGameDataById":
				System.out.println("PlayerController:GetGameDataById");
				getGameDataById(request, response);
				break;		
			default:
				System.out.println("PlayerController:defaultAction");
				defaultAction(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void defaultAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		Player player = null;
		if (id != null && id.length() > 0) {
			player = ECYatzyDB.getPlayerById(Integer.parseInt(id));
		}

		request.setAttribute("player", player);
		RequestDispatcher rd = request.getRequestDispatcher("players.jsp");
		rd.forward(request, response);
	}

	private void getAllPlayers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Player> players = ECYatzyDB.getAllPlayers();
		// http://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.1/gson-2.6.1.jar
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(players));
	}
	
	private void getPlayerById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		Player player = null;
		if (id != null && !id.isEmpty()) {
			player = ECYatzyDB.getPlayerById(Integer.parseInt(id));
		}
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(player));
	}
	
	private void getHighScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<HighScore> highscores = ECYatzyDB.getHighScores(); 
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(highscores));
	}
	
	private void getGameHistoryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
	
		List<GameHistory> gamehists = ECYatzyDB.getPlayerHistoryById(Integer.parseInt(id)); 
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(gamehists));
		
	}

	private void getGameDataById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
	
		List<GameTurn> gameturns = ECYatzyDB.getGameDataByGameId(Integer.parseInt(id)); 
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(gameturns));
		
	}
	
}
