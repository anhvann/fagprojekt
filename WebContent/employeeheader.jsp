<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<meta charset="utf-8">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="Style.css">
<nav class="navbar">
	<a href="index.jsp"> <img class="img-center img-logo"
		src="http://i.imgur.com/RlR7g0c.png"></a>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li><img class="img-bar" src="http://i.imgur.com/Uf3PuDg.png"></li>
		</ul>
	</div>
	<div class="navigationarea">
		<ul class="nav navbar-nav">
			<li><a href="search.jsp">Search</a></li>
			<li><a href="deposit.jsp?accountID=${accountID}">Deposit</a></li>
			<li><a href="withdraw.jsp?accountID=${accountID}">Withdraw</a></li>
			<li><a href="transfer.jsp?accountID=${accountID}">Transfer</a></li>
			<div class="dropdown">
				<div class="expand">
					<button class="dropbtn" >Account</button>
					<div class="dropdown-content">
						<a href="register.jsp">New user account</a> 
						<a href="#">Close user account</a>
					</div>
				</div>
				<div style="clear: left;"></div>
			</div>
		</ul>
	</div>
	<div id="loginlink">
		<ul class="nav navbar-nav pull-right">
			<li><a href="#">Notifications</a></li>
			<li><a href="login.jsp" text-align="right"><span
					class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		</ul>
	</div>
</nav>
</html>