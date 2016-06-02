<!DOCTYPE HTML><%@page language="java"contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<meta charset="utf-8">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="Style.css">
<nav class="navbar">
	<a href="index.jsp"><img class="img-center img-logo"
		src="http://i.imgur.com/RlR7g0c.png"></a>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li><img class="img-bar" src="http://i.imgur.com/Uf3PuDg.png"></li>
		</ul>
	</div>
<%
String role = (String)session.getAttribute("role");
if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {%>
	<div class="navigationarea">
		<ul class="nav navbar-nav">
			<li><a href="index.html">Home</a></li>
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
			<div class="dropdown">
				<div class="expand">
					<button class="dropbtn" >Account</button>
					<div class="dropdown-content">
						<a href="register.jsp">New user account</a> 
						<a href="#">Close user account</a>
					</div>
				</div>
			</div>
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
			<li><a href="ClientActivity?ID=<%=session.getAttribute("loggedinuser")%>&action=deposit">Deposit</a></li>
			<li><a href="ClientActivity?ID=<%=session.getAttribute("loggedinuser")%>&action=withdraw">Withdraw</a></li>
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