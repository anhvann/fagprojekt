<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<div id="wrap">
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.User" %>
	<%@include file="employeecheck.jsp"%>
	<div class="main">
		<div class="pagetitle">Remove Owner(s)</div>
		<hr width="95%" noshade>
					<div align="center">
			<div class="content">
				<span style="float: left;"> <a
					href="AccountActivity?ID=${cpr}&action=viewaccount&accountID=${accountID}">Back
						to account</a>
				</span><br><br>
			</div>
		</div>
			<label class="control-label col-sm-5">Owners:</label>
			<div class="col-sm-5">
				<%LinkedList<User> users = (LinkedList<User>) request.getAttribute("owners");%>
					<%if (users.size() == 1) {%>
						<%=users.get(0).getName()%> (<%=users.get(0).getCPR()%>)
						<font color=grey>&nbsp;&nbsp;&nbsp;remove</font>
						<br>
					<%} else {%>
					<%for (User user : users){%>
						<%=user.getName()%> (<%=user.getCPR()%>) 
						<form style="display: inline;"
								action="AccountActivity?ID=${cpr}&newCPR=<%=user.getCPR()%>&accountID=${accountID}&action=deleteowner" method="post">
							<button type="submit" name="remove" class="btn-link">remove</button>
						</form>
						<br>
					<%}}%>
			</div>
			<br> <br>
			<div class="col-sm-offset-5 col-sm-5">
				<font size="2"><font color="red">${errormessage}</font></font><br>
			</div>
		<br> <br> <br> <br>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>
