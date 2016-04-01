<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<!DOCTYPE html>
<html lang="en">
<div id="container">
	<title>Bootstrap Example</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<style>
			.carousel-inner img {
				margin:auto;
			}
			.navbar {
				font-family: Verdana;
				font-size: 16px;
				background:#012231;
				border:none;
				margin-bottom: 0;
				border-radius: 0;
				overflow-x: hidden;
			}
			.navbar .nav > li > a {
   				line-height: 15px;
   				padding: 10px;
   				padding-left: 20px;
   				padding-right: 20px;
    				color:  #45a3bb;
			}
			.navbar .nav > li > a:hover, 
			.navbar .nav > li > a:focus,			
			.navbar-default .navbar-nav > .active > a, 
			.navbar-default .navbar-nav > .active > a:hover, 
			.navbar-default .navbar-nav > .active > a:focus {
				color: #ffffff;
    				background: #012231;
 			}
			html, body {
   				margin:0;
   				padding:0;
  				height:100%;
			}
			#container {
  				min-height:100%;
				position:relative;
			}
			#body {
				padding-bottom:50px;
			}
			#footer {
   				position:absolute;
   				bottom:0;
  				height:50px;
				width: 100%;
				background: url("http://i.imgur.com/Uf3PuDg.png");
				background-size: auto 50px;
				color: #959595;
				text-align: center;
				font-size: 12px;
				padding-top:15px;
			}
			.img-bar {
				height: 70px;
   				width: auto;
				margin-left: -40px;
			}
			.img-logo{
				position: absolute;
				height: 60%;
				width: auto;
				bottom: 15%;
				z-index:99;
                                margin-left: -20px;
			}
			/* On small screens, set height to 'auto' for sidenav and grid */
			@media screen and (max-width: 767px) {
				.sidenav {
					height: auto;
					padding: 15px;
				}
				.row.content {height:auto;} 
			}
		</style>
	<div id="header">
		<nav class="navbar navbar-default">
			<div class='container'>
				<a href="index.html"><img class="img-center img-logo" src="http://i.imgur.com/RlR7g0c.png"></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><img class="img-bar" src="http://i.imgur.com/Uf3PuDg.png"></li>
				</ul>
			</div>
			<div class="container">
				<ul class="nav navbar-nav">
					<li class="active"><a href="index.html">Home</a></li>
					<li><a href="#">Welcome</a></li>
					<li><a href="#">Contract</a></li>
					<li><a href="#">Help</a></li>
				</ul>
				<ul class="nav navbar-nav pull-right">
					<li><a href="login.html" text-align="right"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>	
			</div>
		</nav>
	</div>
	<div id="body">
		<div class="container">
			<div class="row">
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
					<!-- indicators -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<!-- first item - active item -->
						<div class="item active">
							<img src="https://scontent-bru2-1.xx.fbcdn.net/hphotos-xla1/v/t1.0-9/1653984_10204808397914267_947175797422262872_n.jpg?oh=93052c25142047259bb83327566dc7e9&oe=575CA541" alt="Anna" style="width:340px;height:380px;">
							<div class="carousel-caption">
								<h3><font color="OrangeRed ">Anna - IBM potential manager</font></h3>
								<p><font color="OrangeRed ">She is the kind, smiley, energic and very determined</font></p>
							</div>
						</div>
						<!-- second item -->
						<div class="item">
							<img src="https://scontent-bru2-1.xx.fbcdn.net/hphotos-xaf1/v/t1.0-0/p206x206/263657_10151335079216145_1016235668_n.jpg?oh=adcc97cfe8102ab659d900d41ac65da4&oe=575F2401" alt="Minh" style="width:600px;height:380px;">
							<div class="carousel-caption">
								<h3>Minh - Web user drawer</h3>
								<p>A Vietnamese boy and cool</p>
							</div>
						</div>
						<!-- third item -->
						<div class="item">
						<img src="https://scontent-bru2-1.xx.fbcdn.net/hphotos-prn2/v/t1.0-9/1385557_225543710942819_447759460_n.jpg?oh=d0ac17b1da8f46bd4f6be179c627d788&oe=57707627" alt="Van" style="width:400px;height:380px;">
							<div class="carousel-caption">
								<h3><font color="Blue">Vân - Charming with the eyes of best designer</font></h3>
								<p><font color="Blue">A little shy girl who is creative, positive but very naive</font></p>
							</div>
						</div>
					</div>
					<!-- Left and right controls -->
					<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			<br>
			<br>	
			<!-- This part is for the 4 columns -->
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<a href="#" class="thumbnail">
							<img src="http://placehold.it/800x400?text=ANYTHING 1" alt="Pulpit Rock" style="width:150px;height:150px">
							<p>Unique 1</p>    
						</a>
					</div>
					<div class="col-md-3">
						<a href="#" class="thumbnail">
							<img src="http://placehold.it/800x400?text=ANYTHING 2" alt="Moustiers Sainte Marie" style="width:150px;height:150px">
							<p>Unique 2</p>
						</a>
					</div>
					<div class="col-md-3">
						<a href="#" class="thumbnail">
							<img src="http://placehold.it/800x400?text=ANYTHING 3" alt="Cinque Terre" style="width:150px;height:150px">
							<p>Unique 3</p>      
						</a>
					</div>
					<div class="col-md-3">
						<a href=""" class="thumbnail">
							<img src="http://placehold.it/800x400?text=ANYTHING 4" alt="Cinque Terre" style="width:150px;height:150px">
							<p>Unique 4</p>      
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<p>Uniccol Bank A/S licensed 2016</p>
	</div>
</div>
</body>
</html>