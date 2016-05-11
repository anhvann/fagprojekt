<!DOCTYPE HTML><%@page language="java"contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
response.addHeader("Pragma", "no-cache"); 
response.addDateHeader ("Expires", 0);
if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {
	response.sendRedirect("index.jsp");
}%>

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
			<li><a href="cdeposit.jsp">Deposit</a></li>
			<li><a href="cwithdraw.jsp">Withdraw</a></li>
			<li><a href="Activity?ID=<%session.getAttribute("loggedinuser")%>&action=ctransfer">Transfer</a></li>
		</ul>
	</div>
	<div id="loginlink">
		<ul class="nav navbar-nav pull-right">
			<li><a href="#">Notifications</a></li>
			<li><a href="logoutredirect.jsp" text-align="right">Logout</a></li>
		</ul>
	</div>
</nav>
</html>