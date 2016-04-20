<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div id="message">
			<font size="5"><font color="grey">Create New Account</font></font>
		</div>
			<form action="Activity?ID=${cpr}&action=createaccount" method="post" target="_self">
				<label class="control-label col-sm-4">Interest:</label>
				<div class="col-sm-8"><input type="text" class="form-control" name="interest" placeholder="Enter interest"/></div>
				<br><br>
				<label class="control-label col-sm-4">Status:</label>
				<div class="col-sm-8"><input type="radio" name="status" value="1" checked/> Active <input type="radio" name="status" value="0"/> Inactive</div>
				<br><br>
				<div class="col-sm-offset-4 col-sm-8"><input type="submit" class="btn btn-default" name="createButton" value="Create"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>