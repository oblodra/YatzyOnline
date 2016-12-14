<!-- Source: http://www.tutorialspoint.com/jsp/jsp_session_tracking.htm -->
<%@ page import="java.io.*,java.util.*"%>
<%
	// Get session creation time.
	Date createTime = new Date(session.getCreationTime());
	// Get last access time of this web page.
	Date lastAccessTime = new Date(session.getLastAccessedTime());

	String title = "Welcome Back to my website";
	Integer visitCount = new Integer(0);
	String visitCountKey = new String("visitCount");
	String userIDKey = new String("userID");
	String userID = new String("ABCD");

	// Check if this is new comer on your web page.
	   if (session.isNew()){
	      title = "Welcome to my website";
	      session.setAttribute(userIDKey, userID);
	      session.setAttribute(visitCountKey,  visitCount);
	   } 
	   visitCount = (Integer)session.getAttribute(visitCountKey);
	   visitCount = visitCount + 1;
	   userID = (String)session.getAttribute(userIDKey);
	   session.setAttribute(visitCountKey,  visitCount);
%>
<!DOCTYPE html>
<html>
<head>
<title>Session Tracking</title>
<link rel="stylesheet" href="css/site.css" />
<link rel="stylesheet" href="css/index.css" />
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

	<h1>Session Tracking</h1>

	<table>
		<tr bgcolor="#949494">
			<th>Session info</th>
			<th>Value</th>
		</tr>
		<tr>
			<td>id</td>
			<td>
				<%
					out.print(session.getId());
				%>
			</td>
		</tr>
		<tr>
			<td>Creation Time</td>
			<td>
				<%
					out.print(createTime);
				%>
			</td>
		</tr>
		<tr>
			<td>Time of Last Access</td>
			<td>
				<%
					out.print(lastAccessTime);
				%>
			</td>
		</tr>
		<tr>
			<td>User ID</td>
			<td>
				<%
					out.print(userID);
				%>
			</td>
		</tr>
		<tr>
			<td>Number of visits</td>
			<td>
				<%
					out.print(visitCount);
				%>
			</td>
		</tr>
	</table>
</body>
</html>