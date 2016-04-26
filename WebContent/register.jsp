<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<div class="pagetitle">Create new user account</div>
		<hr width="95%" noshade>
		<div class="pagesubtitle">Please enter all the required information</div>
			<form action="General" method="post" target="_self">
				<label class="control-label col-sm-5" for="email">Email*:</label>
				<div class="col-sm-5"><input type="email" class="form-control" id="email" placeholder="Enter email"></div>
				<br><br>
				<label class="control-label col-sm-5" for="pwd">Password*:</label>
				<div class="col-sm-5"> <input type="password" class="form-control" id="pwd" placeholder="Enter password"></div>
				<br><br>
				<label class="control-label col-sm-5" for="firstname">First Name*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" id="name" placeholder="Enter first name"></div>
				<br><br>
				<label class="control-label col-sm-5" for="lastname">Last Name*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" id="name" placeholder="Enter last name"></div>
				<br><br>
				<label class="control-label col-sm-5" for="address">Address*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" id="address" placeholder="Enter address"></div>
				<br><br>
				<label class="control-label col-sm-5" for="address">Zip code*:</label>
				<div class="col-sm-5"><input type="number" class="form-control" id="zipcode" placeholder="Enter zipcode"></div>
				<br><br>
				<label class="control-label col-sm-5" for="city">City*:</label>
				<div class="col-sm-5"><input type="text" class="form-control" id="city" placeholder="Enter city"></div>
				<br><br>
				
				<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${message}</font></font><br>
				<input type="submit" class="btn btn-default" name="registerButton" value="Create"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>
