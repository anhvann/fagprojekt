<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<div class="pagetitle">Deposit money</div>
		<hr width="95%" noshade>
			<form action="General" method="post" target="_self">
				<label class="control-label col-sm-5">Account:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="accountID" placeholder="Enter account"/></div>
				<br><br>
				<label class="control-label col-sm-5">Amount:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="depositAmount" placeholder="Enter amount"/></div>
				<br><br>
				<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${message}</font></font><br>
				<input type="submit" class="btn btn-default" name="depositButton" value="Deposit"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>