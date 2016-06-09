<!DOCTYPE HTML><%@page language="java"contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<input type="hidden" id="refreshed" value="no">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="Style.css">
<nav class="navbar">
	<a href="index.jsp"><img class="img-center img-logo" src="http://i.imgur.com/m1Fk2Mt.png"></a>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li><img class="img-bar" src="http://i.imgur.com/Uf3PuDg.png"></li>
		</ul>
	</div>
<%String role = (String)session.getAttribute("role");
if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {%>
	<div class="navigationarea">
		<ul class="nav navbar-nav">
			<li><a href="index.jsp">Home</a></li>
			<li><a href="#">Welcome</a></li>
			<li><a href="#">Contact</a></li>
			<li><a href="#">Help</a></li>
		</ul>
			<div id="loginlink">
		<ul class="nav navbar-nav pull-right">
			<li><a href="login.jsp" text-align="right">Login</a></li>
		</ul>
	</div>
<%} else if(role.equals("e")){%>
	<div class="navigationarea">
		<ul class="nav navbar-nav">
			<li><a href="search.jsp">Search</a></li>
			<li><a href="deposit.jsp?accountID=${accountID}&accountName=${accountName}">Deposit</a></li>
			<li><a href="withdraw.jsp?accountID=${accountID}&accountName=${accountName}">Withdraw</a></li>
			<li><a href="transfer.jsp?accountID=${accountID}&accountName=${accountName}">Transfer</a></li>
			<li><a href="register.jsp?">Register User</a></li>
		</ul>
	</div>
	<div id="loginlink">
		<ul class="nav navbar-nav pull-right">
			<li><a href="logoutredirect.jsp" text-align="right">Logout</a></li>
		</ul>
	</div>
<%} else{ %>
	<div class="navigationarea">
		<ul class="nav navbar-nav">
			<li><a href="UserActivity?ID=<%=session.getAttribute("loggedinuser")%>&action=viewuser">Accounts</a></li>
			<li><a href="ClientActivity?ID=<%=session.getAttribute("loggedinuser")%>&action=transfer">Transfer</a></li>
		</ul>
	</div>
	<div id="loginlink">
		<ul class="nav navbar-nav pull-right">
			<li><a href="logoutredirect.jsp" text-align="right">Logout</a></li>
		</ul>
	</div>
<%} %>
</nav>
</html>