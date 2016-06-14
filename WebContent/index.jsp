<!DOCTYPE html>
<html>
<div id="wrap">
	<%@include file="header.jsp"%>
	<div class="front">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<img src="http://i.imgur.com/dqbAEoA.png">
				</div>
				<!-- second item -->
				<div class="item">
					<img src="http://i.imgur.com/SXeoyIw.png">
				</div>
				<!-- third item -->
				<div class="item">
					<a href="buysellrate.jsp"><img
						src="http://i.imgur.com/Nc3Lygm.png"></a>
				</div>
			</div>
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
</div>
	<%@include file="footer.jsp"%>
</html>