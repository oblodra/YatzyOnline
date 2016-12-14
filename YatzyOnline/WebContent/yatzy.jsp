<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.yatzy.models.GameData"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<% Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yatzy</title>
<link rel="stylesheet" href="css/site.css" />
<link rel="stylesheet" href="css/DBAccessStyle.css"/>
</head>

<body>
	<nav>
		<a href="<%=request.getContextPath()%>/index.jsp">Start Page</a>
		<a href="<%=request.getContextPath()%>/yatzy.jsp">Yatzy</a>
		<a href="<%=request.getContextPath()%>/highscorelist.jsp">Highscore List</a>
		<a href="<%=request.getContextPath()%>/players.jsp">Players</a>
		<a href="<%=request.getContextPath()%>/gamehistory.jsp">Gamehistory</a>
		<a href="<%=request.getContextPath()%>/gamedata.jsp">Gamedata</a>
	</nav>
		
	<div id="gameSetup"">
	<h1>Yatzy setup</h1>
	<form action="yatzy" method="get">

	  <fieldset>
	    <legend class="playSettCl">Player settings</legend>
			<legend class="p1">Player 1</legend>
    		
    		<input type="radio" id="human_p1" class="p1" name="player1" value="human" checked>
    		<label for="human_p1" class="p1">Human</label>
    		<input type="radio" id="computer_p1" class="p1" name="player1"  value="computer">
    		<label for="computer_p1" class="p1">Computer</label>
    		<input name="player1_firstname" class="p1" type="text" value="tesf1" >  <%-- placeholder="FirstName" required --%>
			<input name="player1_lastname" class="p1" type="text" value="tesl1" > <%-- placeholder="LastName" required --%>
			
			<legend class="p2">Player 2</legend>
    		<input type="radio" id="human_p2" class="p2" name="player2" value="human" checked>
    		<label for="human_p2" class="p2">Human</label>
    		<input type="radio" id="computer_p2" class="p2" name="player2"  value="computer">
    		<label for="computer_p2" class="p2">Computer</label>
    		<input name="player2_firstname" class="p2" type="text" value="tesf2" > <%-- placeholder="FirstName" required --%>
			<input name="player2_lastname" class="p2" type="text" value="tesl2" >   <%-- placeholder="LastName" required --%>  		
	  
	  		<legend class="p3">Rules</legend>
	  		<input type="radio" id="rule1" class="rulesCl" name="rules" value="ruleOne" checked>
		  	<label for="rul1" class="rulesCl">UpDown</label>
		  	<input type="radio" id="rule2" class="rulesCl" name="rules" value="ruleTwo">
		  	<label for="rule2" class="rulesCl">UpThenDown</label>
		  	<input type="radio" id="rule3" class="rulesCl" name="rules" value="ruleThree">
	  		<label for="rule3" class="rulesCl">Optional</label>
	  		
	  		<input id="setupGame-submit" type="submit" value="Starta nytt spel"/>
	  </fieldset>		
			
	
	
	</form>
	</div>		
	
	<div id="setupGame"></div>

	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	<script src="script/yatzy.js"></script>
</body>
</html>