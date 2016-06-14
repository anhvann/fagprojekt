<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div id="wrap">
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.User" %>
	<%@include file="employeecheck.jsp"%>
	<div class="main">
		<div class="pagetitle">Add Owner</div>
		<hr width="95%" noshade>
			<div align="left">
			<a href="AccountActivity?ID=${cpr}&action=viewaccount&accountID=${accountID}">Back to
								account</a>
			</div>
		<form
			action="AccountActivity?ID=${cpr}&action=share&accountID=${accountID}"
			method="post" target="_self">
			<label class="control-label col-sm-5">CPR:</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" name="newCPR"
					placeholder="Enter CPR" value="${newCPR}">
			</div>
			<br> <br>
			<div class="col-sm-offset-5 col-sm-5">
				<font size="2"><font color="red">${errormessage}</font></font><br>
				<input type="submit" class="btn btn-default" name="saveChanges"
					value="Add Owner">
			</div>
		</form>
		<br> <br> <br> <br>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>
