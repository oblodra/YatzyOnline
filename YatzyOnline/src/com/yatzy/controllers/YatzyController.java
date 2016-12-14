package com.yatzy.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yatzy.models.GameData;
import com.yatzy.models.GameSetup;
import com.yatzy.models.EndGameData;

/**
 * Servlet implementation class YatzyController
 */
@WebServlet("/yatzy")
public class YatzyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    GameSetup gamesetup;  
    boolean startup = false;;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YatzyController() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		String url;
		GameData gamedata = (GameData) session.getAttribute("gamedata");
		
		if (gamedata == null) {
			System.out.println("gamedata is null, initializing a new game");
			
			gamesetup = new GameSetup(  request.getParameter("player1"), 
										request.getParameter("player1_firstname"),
										request.getParameter("player1_lastname"),
										request.getParameter("player2"),
										request.getParameter("player2_firstname"),
										request.getParameter("player2_lastname"),
										request.getParameter("rules"),
										2);

						
			gamedata = new GameData(gamesetup.getFirstName(0), gamesetup.getLastName(0), 
									gamesetup.getFirstName(1), gamesetup.getLastName(1), 
									1,0);
			
			EndGameData enddata = null;
			
			gamesetup.startNewWebGame();
			
			//test
			
			//gamedata.setSavePossible(true);
			
			//boolean[] ld = {false, true, false, false, true};
			//gamedata.setDiceLocks(ld);
			startup = true;
		}
		
		boolean[] ld = {((request.getParameter("lockDice1") != null) ? true : false), 
						((request.getParameter("lockDice2") != null) ? true : false), 
						((request.getParameter("lockDice3") != null) ? true : false), 
						((request.getParameter("lockDice4") != null) ? true : false), 
						((request.getParameter("lockDice5") != null) ? true : false)};

		String saveLoc = request.getParameter("diceAction");
		
		//update gameEngine with data from webpage
		/*
		System.out.println(request.getParameter("lockDice1"));
		System.out.println(request.getParameter("lockDice2"));
		System.out.println(request.getParameter("lockDice3"));
		System.out.println(request.getParameter("lockDice4"));
		System.out.println(request.getParameter("lockDice5"));
		System.out.println(request.getParameter("diceAction"));
		*/
		
		gamesetup.updateEngine(ld, saveLoc);
		
		//update gameSetup
		
		//update gamedata with scoreboard, diceresult, what buttons are enabled and what dice are locked
		
		//(obsolete)gamedata.setGameOver(gamesetup.getEngine().gameOver());
		
		
		//check if game is over
		if (gamesetup.getEngine().gameDone()) {
			
			//game is over gamedata set to null, and data needed for making endgame.jsp i created in model EndGameData
			//obsolete
			gamedata.setGameOverData(gamesetup.getEngine().getEndGameScores());
			
			//EndGameData
			EndGameData endData = new EndGameData(gamesetup.getEngine().getPlayerNames(), gamesetup.getEngine().getWebData(), gamesetup.getEngine().getWinnerID());
			
			gamedata = null;
			gamesetup = null;
			
			session.setAttribute("endData", endData);
			session.setAttribute("gamedata", gamedata);
			
			url = "/endGame.jsp";
			
		}		
		else {
			if(startup) {
				startup = false;
				//int[] dr = {1, 1, 1, 1, 1};
				//gamedata.setDiceResult(dr);
				
				//clear it if its the second+ game
				EndGameData endData = null;
				session.setAttribute("endData", endData);
				
				//url = "/playYatzy.jsp";
			
				//session.setAttribute("gamedata", gamedata);
			}		
			
			//playing
			gamedata.setWebData(gamesetup.getEngine().getWebData());
			//System.out.println("Example webData " + gamedata.getTableData(0,5));
			
			gamedata.setActivePlayer(gamesetup.getEngine().getActivePlayer());
			System.out.println("Activeplayer " + gamedata.getActivePlayer());
			
			gamedata.setTurn(gamesetup.getEngine().getCurrentTurn());
			//System.out.println("Example webData " + gamedata.getTurn());
			
			gamedata.setDiceLocks(gamesetup.getEngine().getLockedDices());
			
			gamedata.setEnabledButtons(gamesetup.getEngine().getEnabledButtons());
			
			gamedata.setButtonID(gamesetup.getEngine().getButtonID());
			
			gamedata.setDiceResult(gamesetup.getEngine().getDiceResult());
			
			url = "/playYatzy.jsp";
			
			session.setAttribute("gamedata", gamedata);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
