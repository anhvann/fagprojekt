<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<div class="pagetitle">Create new account</div>
		<hr width="95%" noshade>
			<form action="AccountActivity?ID=${cpr}&action=createaccount" method="post" target="_self">
				<label class="control-label col-sm-5">Name:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="name" placeholder="Enter name"/></div>
				<br><br>
				<label class="control-label col-sm-5">Interest:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="interest" placeholder="Enter interest"/></div>
				<br><br>
				<label class="control-label col-sm-5">Status:</label>
				<div class="col-sm-5"><input type="radio" name="status" value="1" checked/> Active <input type="radio" name="status" value="0"/> Inactive</div>
				<br><br>
				<div class="col-sm-offset-5 col-sm-5">
				<input type="submit" class="btn btn-default" name="createButton" value="Create">
				</div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>