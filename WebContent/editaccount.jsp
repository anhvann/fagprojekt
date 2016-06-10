<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div class="container">
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.User" %>
	<%@include file="employeecheck.jsp" %>
	<div class="main" >
			<div class="pagetitle">Edit Account</div>
			<hr width="95%" noshade>
				<form action="AccountActivity?ID=${cpr}&action=changeaccount&accountID=${accountID}" method="post" target="_self">
					<label class="control-label col-sm-5">Owner(s):</label>
					<div class="col-sm-5">
						<%LinkedList<User> users = (LinkedList<User>) request.getAttribute("owners");%>
						<%for (User user : users){%>
							<%=user.getName()%> (<%=user.getCPR()%>) 
							<a href="AccountActivity?ID=<%user.getCPR();%>&accountID=${accountID}&action=deleteowner">Remove</a>
							<br>
						<%}%>
					</div>
					<br><br>
					<label class="control-label col-sm-5">Name:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="accountName" placeholder="Enter name" value="${name}">
					</div>
					<br><br>
					
					<label class="control-label col-sm-5">Interest:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="interest" placeholder="Enter interest" value="${interest}">
					</div>
					<br><br>
					<label class="control-label col-sm-5">Currency:</label>
					<div class="col-sm-5">
					<input type="text" class="form-control" name="ISOCode" readonly="readonly" value="${ISOCode}">
	  				</div>
					<br><br>
					
					<div class="col-sm-offset-5 col-sm-5">
						<input type="submit" class="btn btn-default" name="saveChanges" value="Save Changes">
					</div>
				</form>	
			</hr>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>
