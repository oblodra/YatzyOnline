<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<% Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamehistory</title>
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
	
	<h1>Gamehistory</h1>
	<form>
		<label for="playerID">Enter the ID of the Player you see the gamehistory for</label><br /> 
		<input id="playerID" type="Number" name="id" min="1" />
		<input id="btn-submit" type="submit" /><br />
	</form>
	
	<div id="playResult"></div>

	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	<script src="script/gamehistory.js"></script>
</body>
</html>