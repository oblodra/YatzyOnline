<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.yatzy.models.GameData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PlayYatzy</title>
<link rel="stylesheet" href="css/site.css" />
<link rel="stylesheet" href="css/playYatzy.css"/>
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
		
	<div id="gameSetup">
	<h1>Play Yatzy</h1>
<form action="yatzy" method="get">

<div id="left">
<table>
  <tr>
    <th>ScoreTypes</th>
    <th><c:out value = "${gamedata.getPlayer1Name()}" /></th>
    <th><c:out value = "${gamedata.getPlayer2Name()}" /></th>
  </tr>
  <tr>
    <td><input id="ones-submit" name="diceAction" type="submit" value="Ones" ${ gamedata.isButtonActive(0) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,0)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,0)}" /></td>
  </tr>
  <tr>
    <td><input id="twos-submit" name="diceAction" type="submit" value="Twos" ${ gamedata.isButtonActive(1) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,1)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,1)}" /></td>
  </tr>
    <tr>
    <td><input id="threes-submit" name="diceAction" type="submit" value="Threes" ${ gamedata.isButtonActive(2) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,2)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,2)}" /></td>
  </tr>
  <tr>
    <td><input id="fours-submit" name="diceAction" type="submit" value="Fours" ${ gamedata.isButtonActive(3) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,3)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,3)}" /></td>
  </tr>
    <tr>
    <td><input id="fives-submit" name="diceAction" type="submit" value="Fives" ${ gamedata.isButtonActive(4) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,4)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,4)}" /></td>
  </tr>
  <tr>
    <td><input id="sixes-submit" name="diceAction" type="submit" value="Sixes" ${ gamedata.isButtonActive(5) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,5)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,5)}" /></td>
  </tr>
    
   <tr>
    <td class="score">Bonus</td>
    <td class="score"><c:out value = "${gamedata.getTableData(0,15)}" /></td>
    <td class="score"><c:out value = "${gamedata.getTableData(1,15)}" /></td>
  </tr>
 
    <tr>
    <td><input id="onepair-submit" name="diceAction" type="submit" value="One Pair" ${ gamedata.isButtonActive(6) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,6)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,6)}" /></td>
  </tr>
  <tr>
    <td><input id="twopairs-submit" name="diceAction" type="submit" value="Two pairs" ${ gamedata.isButtonActive(7) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,7)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,7)}" /></td>
  </tr>
    <tr>
    <td><input id="threeofakind-submit" name="diceAction" type="submit" value="Three of a Kind" ${ gamedata.isButtonActive(8) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,8)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,8)}" /></td>
  </tr>
  <tr>
    <td><input id="fourofakind-submit" name="diceAction" type="submit" value="Four of a Kind" ${ gamedata.isButtonActive(9) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,9)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,9)}" /></td>
  </tr>
    <tr>
    <td><input id="smallstraight-submit" name="diceAction" type="submit" value="Small Straight" ${ gamedata.isButtonActive(10) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,10)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,10)}" /></td>
  </tr>
  <tr>
    <td><input id="largestraight-submit" name="diceAction" type="submit" value="Large Straight" ${ gamedata.isButtonActive(11) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,11)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,11)}" /></td>
  </tr>
    <tr>
    <td><input id="fullhouse-submit" name="diceAction" type="submit" value="Full House" ${ gamedata.isButtonActive(12) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,12)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,12)}" /></td>
  </tr>
  <tr>
    <td><input id="chance-submit" name="diceAction" type="submit" value="Chance" ${ gamedata.isButtonActive(13) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,13)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,13)}" /></td>
  </tr>
  <tr>
    <td><input id="yatzy-submit" name="diceAction" type="submit" value="Yatzy" ${ gamedata.isButtonActive(14) ? '' : 'disabled="disabled"'} /></td>
    <td><c:out value = "${gamedata.getTableData(0,14)}" /></td>
    <td><c:out value = "${gamedata.getTableData(1,14)}" /></td>
  </tr>
     <tr>
    <td class="score">Total</td>
    <td class="score"><c:out value = "${gamedata.getTableData(0,16)}" /></td>
    <td class="score"><c:out value = "${gamedata.getTableData(1,16)}" /></td>
  </tr>
  
</table> 
</div>
<div id="middle"></div>


</div>
<div id="right">
<table>
    <tr>
    <th>Lock dice 1</th>
    <th>Lock dice 2</th>
    <th>Lock dice 3</th>
    <th>Lock dice 4</th>
    <th>Lock dice 5</th>
  </tr>
  <tr>
    <td><input  name= "lockDice1" type="checkbox" value="Lock" ${ gamedata.isCheckBoxActive() ? '' : 'disabled="disabled"'} ${ gamedata.isCheckBoxChecked(0) ? 'checked="checked"' : ''} /></td> 
    <td><input  name= "lockDice2" type="checkbox" value="Lock" ${ gamedata.isCheckBoxActive() ? '' : 'disabled="disabled"'} ${ gamedata.isCheckBoxChecked(1) ? 'checked="checked"' : ''} /></td>
    <td><input  name= "lockDice3" type="checkbox" value="Lock" ${ gamedata.isCheckBoxActive() ? '' : 'disabled="disabled"'} ${ gamedata.isCheckBoxChecked(2) ? 'checked="checked"' : ''} /></td>
    <td><input  name= "lockDice4" type="checkbox" value="Lock" ${ gamedata.isCheckBoxActive() ? '' : 'disabled="disabled"'} ${ gamedata.isCheckBoxChecked(3) ? 'checked="checked"' : ''} /></td>
    <td><input  name= "lockDice5" type="checkbox" value="Lock" ${ gamedata.isCheckBoxActive() ? '' : 'disabled="disabled"'} ${ gamedata.isCheckBoxChecked(4) ? 'checked="checked"' : ''} /></td>
  </tr>
  <tr>
    <td><c:out value = "${gamedata.getDiceById(0)}" /></td>
    <td><c:out value = "${gamedata.getDiceById(1)}" /></td>
    <td><c:out value = "${gamedata.getDiceById(2)}" /></td>
    <td><c:out value = "${gamedata.getDiceById(3)}" /></td>
    <td><c:out value = "${gamedata.getDiceById(4)}" /></td>
  </tr>
</table>

<input name="diceAction" type="submit" ${ gamedata.getButtonText() }  ${ gamedata.isButtonActive() ? '' : 'disabled="disabled"'} />

</div>
	
	</form>
	</div>		
	

	
	
	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	<script src="script/playYatzy.js"></script>
</body>
</html>