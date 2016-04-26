<!DOCTYPE html>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<form id="loginform" action="Login" method="post" target="_self">
			<legend>Login to Uniccol Bank</legend>
			CPR Number:<br> <input type="text" name="cpr"><br>
			Password:<br> <input type="text" name="password"><br>
			<font size="2"><font color="red">${message}</font></font> <br>
			<input type="submit" class="btn btn-default" name="loginButton" value="Login"></div>
		</form>
	</div>
	<%@include file="footer.jsp"%>
</div>
</html>