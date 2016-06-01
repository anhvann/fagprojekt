<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="sessioncheck.jsp" %>
	<div class="main">
		<div class="pagetitle">Create new user account</div>
		<hr width="95%" noshade>
		<div class="pagesubtitle">Please enter all the required information</div>
			<form action="UserActivity?action=register" method="post" target="_self">
				<label class="control-label col-sm-5" for="email">Email*:</label>
				<div class="col-sm-5"><input type="email" class="form-control" name="email" placeholder="Enter email"></div>
				<br><br>
				<label class="control-label col-sm-5" for="ID">CPR*:</label>
				<div class="col-sm-5"><input type="number" class="form-control" name="ID" placeholder="Enter CPR number" maxlength="10"></div>
				<br><br>
				<label class="control-label col-sm-5" for="password">Password*:</label>
				<div class="col-sm-5"> <input type="password" class="form-control" name="password" placeholder="Enter password"></div>
				<br><br>
				<label class="control-label col-sm-5" for="phone">Phone*:</label>
				<div class="col-sm-5"> <input type="number" class="form-control" name="phone" placeholder="Enter phone number" maxlength="8"></div>
				<br><br>
				<label class="control-label col-sm-5" for="name">Full Name*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="name" placeholder="Enter full name"></div>
				<br><br>
				<label class="control-label col-sm-5" for="address">Address*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="address" placeholder="Enter address"></div>
				<br><br>
				<label class="control-label col-sm-5" for="zipcode">Zip code*:</label>
				<div class="col-sm-5"><input type="number" class="form-control" name="zipcode" placeholder="Enter zipcode" maxlength="4"></div>
				<br><br>
				<label class="control-label col-sm-5" for="date">Date of birth*:</label>
				<div class="col-sm-5"><input type="Date" class="form-control" name="date" placeholder="Enter date of birth"></div>
				<br><br>				
				<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${message}</font></font><br>
				<input type="submit" class="btn btn-default" name="registerButton" value="Create"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>
