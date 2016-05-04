<!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Account" %>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<div class="pagetitle">	${fullname} (${cpr})</div>
		<hr width="95%" noshade>
		<div align="center">
			<div class="content">
				<p style="text-align:left;">
				<a href="Activity?ID=${cpr}&action=edit">Edit User Information</a><br>
				<a href="Activity?ID=${cpr}&action=newaccount">Create New Account</a>
				</p>
			</div>
		
			<table class="clickable">
			    <col width="20%">
		  		<col width="50%">
		  		<col width="30%">
				<tr>
					<th>Account</th>
					<th>Name</th>
					<th>Balance</th>
				</tr>
				<%
				LinkedList<Account> acc = (LinkedList<Account>) request.getAttribute("accounts");
				for (int i = 0; i < acc.size(); i++) {%>
				<tr onclick="document.location = 'Activity?ID=${cpr}&action=viewaccount&accountID=<%=acc.get(i).getAccountID()%>';">
					<td> <%= acc.get(i).getAccountID() %></td>
					<td> <%= acc.get(i).getName() %></td>
					<td> <%= acc.get(i).getBalanceString() %></td>
				</tr>
				<%}%>
			</table>
		</div>
	</div>
	</table>
</div>
<%@include file="footer.jsp"%>
</html>
