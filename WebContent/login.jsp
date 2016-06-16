<!DOCTYPE html>
<html>
<div id="wrap">
	<%@include file="header.jsp"%>
	<%if ((session.getAttribute("loggedinuser") != null) && (session.getAttribute("loggedinuser") != "")) {
	response.sendRedirect("index.jsp");
	}%>
	<div class="main">
		<form id="loginform" action="Login" method="post" target="_self">
			<legend>Login to Uniccol Bank</legend>
			ID:<br> <input type="number" name="cpr" required><br>
			Password:<br> <input type="password" name="password" required><br>
			<font size="2"><font color="red">${errormessage}</font></font> <br>
			<input type="submit" class="btn btn-default" name="loginButton" value="Login"></div>
		</form>
	</div>
	<%@include file="footer.jsp"%>
</div>
</html>