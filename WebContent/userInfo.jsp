<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main" >
			<div class="pagetitle">Edit User Information</div>
			<hr width="95%" noshade>
				<form action="Activity" method="post" target="_self">
					<label class="control-label col-sm-5" for="email">Email:</label>
					<div class="col-sm-5"><input type="email" class="form-control" id="email" placeholder="Enter email" value=${email}></div>
					<br><br>
					<label class="control-label col-sm-5" for="pwd">Password:</label>
					<div class="col-sm-5"><input type="password" class="form-control" id="pwd" placeholder="Enter password" value=${password}></div>
					<br><br>
					<label class="control-label col-sm-5" for="fullname">Full Name:</label>
					<div class="col-sm-5"><input type="text" class="form-control" id="name" placeholder="Enter full name" value=${fullname}></div>
					<br><br>
					<label class="control-label col-sm-5" for="address">Address:</label>
					<div class="col-sm-5"><input type="text" class="form-control" id="address" placeholder="Enter address" value=${address}></div>
					<br><br>
					<label class="control-label col-sm-5" for="address">Zipcode:</label>
					<div class="col-sm-5"><input type="number" class="form-control" id="zipcode" placeholder="Enter zip code" value=${postcode}></div>
					<br><br>
					<label class="control-label col-sm-5">City:</label>
					<div class="col-sm-5"><input type="text" class="form-control" id="city" placeholder="Enter city"></div>
					<br><br>
					
					<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${message}</font></font><br>
					<input type="submit" class="btn btn-default" name="depositButton" value="Save Changes"></div>
				</form>	
	</div>
</div>
<%@include file="footer.jsp"%>
</html>

