<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>search</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<!DOCTYPE html>
<html lang="en">
<div id="container">
	<title>Bootstrap Example</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
		href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style>
.carousel-inner img {
	margin: auto;
}

.navbar {
	font-family: Verdana;
	font-size: 16px;
	background: #012231;
	border: none;
	margin-bottom: 0;
	border-radius: 0;
	overflow-x: hidden;
}

.navbar .nav>li>a {
	line-height: 15px;
	padding: 10px;
	padding-left: 20px;
	padding-right: 20px;
	color: #45a3bb;
}

.navbar .nav>li>a:hover, .navbar .nav>li>a:focus, .navbar-default .navbar-nav>.active>a,
	.navbar-default .navbar-nav>.active>a:hover, .navbar-default .navbar-nav>.active>a:focus
	{
	color: #ffffff;
	background: #012231;
}

html, body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#container {
	min-height: 100%;
	position: relative;
}

#body {
	padding-bottom: 50px;
}

#footer {
	position: absolute;
	bottom: 0;
	height: 50px;
	width: 100%;
	background: url("http://i.imgur.com/Uf3PuDg.png");
	background-size: auto 50px;
	color: #959595;
	text-align: center;
	font-size: 12px;
	padding-top: 15px;
}

.img-bar {
	height: 80px;
	width: auto;
	margin-left: -40px;
}

.img-logo {
	position: absolute;
	height: 65px;
	width: auto;
	bottom: 15%;
	z-index: 99;
	margin-left: -20px;
	margin-bottom: 10px;
}

.searchbar input {
	box-sizing: border-box;
	padding: 20px;
	font-style: italic;
	border: none;
	background: #F0F0F0;
	font-size: 20px;
}

#links {
	width: 1000px;
	margin: 0 auto;
}
/* On small screens, set height to 'auto' for sidenav and grid */
@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}
</style>
	<div id="header">
		<nav class="navbar navbar-default">
			<div id="links">
				<a href="index.html"><img class="img-center img-logo"
					src="http://i.imgur.com/RlR7g0c.png"></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><img class="img-bar" src="http://i.imgur.com/Uf3PuDg.png"></li>
				</ul>
			</div>
			<div id="wrapper">
				<div id="links">
					<ul class="nav navbar-nav">
						<li><a href="index.html">Home</a></li>
						<li><a href="#">Welcome</a></li>
						<li><a href="#">Contact</a></li>
						<li><a href="#">Help</a></li>
					</ul>
				</div>
				<div id="loginlink">
					<ul class="nav navbar-nav pull-right">
						<li><a href="login.html" text-align="right"><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	<div id="body">
		<div class="searchbar">
			<form action="action_page.php" method="post" target="_blank">
				<input type="submit" value="Search" style="float: right" />
				<div style="overflow: hidden">
					<input type="text" name="search"
						placeholder="Search by name, cpr or account number"
						style="width: 100%">
				</div>
				​
			</form>
		</div>
	</div>
	<div id="footer">
		<p>Uniccol Bank A/S licensed 2016</p>
	</div>
</div>
</body>
</html>