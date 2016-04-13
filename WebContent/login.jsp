<!DOCTYPE html>
<%@include file="header.jsp"%>
<body>
	<div class="container">
			<form id="loginform" action="Login" method="post" target="_self">
				<legend>Login to Uniccol Bank</legend>
				E-mail address:<br> <input type="text" name="email"><br>
				Password:<br> <input type="text" name="password"><br>
				<font size="2"><font color="red">${param.message}</font></font> <br>
				<input type="submit" name="loginButton" value="Login">
			</form>
	</div>
</body>
<%@include file="footer.jsp"%>
</html>