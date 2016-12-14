<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.yatzy.models.EndGameData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>End result Yatzy game</title>
<link rel="stylesheet" href="css/site.css" />
<link rel="stylesheet" href="css/playYatzy.css" />
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
		
<div id="points">
	<h1>End result Yatzy game</h1>

 <table>
  <tr>
    <th>Scoretypes</th>
    <th><c:out value = "${endData.getName(0)}" /></th>
    <th><c:out value = "${endData.getName(1)}" /></th>
  </tr>
  <tr>
    <td>Ones</td>
    <td><c:out value = "${endData.getTableData(0, 0)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 0)}" /></td>
  </tr>  
    <tr>
    <td>Twos</td>
    <td><c:out value = "${endData.getTableData(0, 1)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 1)}" /></td>
  </tr> 
    <tr>
    <td>Threes</td>
    <td><c:out value = "${endData.getTableData(0, 2)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 2)}" /></td>
  </tr> 
    <tr>
    <td>Fours</td>
    <td><c:out value = "${endData.getTableData(0, 3)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 3)}" /></td>
  </tr> 
    <tr>
    <td>Fives</td>
    <td><c:out value = "${endData.getTableData(0, 4)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 4)}" /></td>
  </tr> 
    <tr>
    <td>Sixes</td>
    <td><c:out value = "${endData.getTableData(0, 5)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 5)}" /></td>
  </tr> 
    <tr>
    <td class="score">Bonus</td>
    <td class="score"><c:out value = "${endData.getTableData(0, 15)}" /></td>
    <td class="score"><c:out value = "${endData.getTableData(1, 15)}" /></td>
  </tr> 
    <tr>
    <td>One Pair</td>
    <td><c:out value = "${endData.getTableData(0, 6)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 6)}" /></td>
  </tr> 
    <tr>
    <td>Two Pairs</td>
    <td><c:out value = "${endData.getTableData(0, 7)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 7)}" /></td>
  </tr> 
    <tr>
    <td>Three of a Kind</td>
    <td><c:out value = "${endData.getTableData(0, 8)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 8)}" /></td>
  </tr> 
    <tr>
    <td>Four of a Kind</td>
    <td><c:out value = "${endData.getTableData(0, 9)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 9)}" /></td>
  </tr> 
    <tr>
    <td>Small Straight</td>
    <td><c:out value = "${endData.getTableData(0, 10)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 10)}" /></td>
  </tr> 
    <tr>
    <td>Large Straight</td>
    <td><c:out value = "${endData.getTableData(0, 11)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 11)}" /></td>
  </tr> 
    <tr>
    <td>Full House</td>
    <td><c:out value = "${endData.getTableData(0, 12)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 12)}" /></td>
  </tr> 
    <tr>
    <td>Chance</td>
    <td><c:out value = "${endData.getTableData(0, 13)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 13)}" /></td>
  </tr> 
    <tr>
    <td>Yatzy</td>
    <td><c:out value = "${endData.getTableData(0, 14)}" /></td>
    <td><c:out value = "${endData.getTableData(1, 14)}" /></td>
  </tr> 
    <tr>
    <td class="score">Total Sum</td>
    <td class="score"><c:out value = "${endData.getTableData(0, 16)}" /></td>
    <td class="score"><c:out value = "${endData.getTableData(1, 16)}" /></td>
  </tr> 
  
</table> 
</div>
<div id="congratz">
<p>
Congratulations <c:out value = "${endData.getName(endData.getWinnerId())}" /> , you won with <c:out value = "${endData.getTableData(endData.getWinnerId(), 16)}" /> points!
</p>
</div>

<div id="newgame">
<p>
<p><a href="<%=request.getContextPath()%>/yatzy.jsp">Start a new game!</a></p>
</p>
</div>
	
	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	<script src="script/playYatzy.js"></script>
</body>
</html>