<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeecheck.jsp" %>
	<div class="main" >
			<div class="pagetitle">Edit User Information</div>
			<hr width="95%" noshade>
				<form action="UserActivity?ID=${cpr}&action=change" method="post" target="_self">
					<label class="control-label col-sm-5" for="email">Email:</label>
					<div class="col-sm-5"><input type="email" class="form-control" name="email" placeholder="Enter email" value="${email}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="pwd">Password:</label>
					<div class="col-sm-5"><input type="password" class="form-control" name="password" placeholder="Enter password" value="${password}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="phone">Phone:</label>
					<div class="col-sm-5"><input type="number" class="form-control" name="phone" placeholder="Enter Phone" value="${phone}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="name">Full Name:</label>
					<div class="col-sm-5"><input type="text" class="form-control" name="name" placeholder="Enter full name" value="${name}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="address">Address:</label>
					<div class="col-sm-5"><input type="text" class="form-control" name="address" placeholder="Enter address" value="${address}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="address">Zipcode:</label>
					<div class="col-sm-5"><input type="number" class="form-control" name="zipcode" placeholder="Enter zip code" value="${postcode}"></div>
					<br><br>
					<label class="control-label col-sm-5">City:</label>
					<div class="col-sm-5"><input type="text" class="form-control" name="city" readonly="readonly" value="${city}"></div>
					<br><br>
					<label class="control-label col-sm-5" for="date">Date of birth*:</label>
					<div class="col-sm-5"><input type="Date" class="form-control" name="date" placeholder="Enter date of birth" value="${date}"></div>
					<br><br>
					
					<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${errormessage}</font></font><br>
					<input type="submit" class="btn btn-default" name="depositButton" value="Save Changes"></div>
				</form>	
	</div>
</div>
<%@include file="footer.jsp"%>
</html>

