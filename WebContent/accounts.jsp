<!DOCTYPE html>
<html>
<div class="container">
	<%@include file="sessioncheck.jsp" %>
	<%@ page import="java.sql.*"%>
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.Account" %>
	<div class="main">
		<div class="pagetitle">	${fullname} (${cpr})</div>
		<hr width="95%" noshade>
		<div align="center">
			<div class="content">
				<p style="text-align:left;">
				<a href="UserActivity?ID=${cpr}&action=edit">Edit User Information</a><br>
				<a href="AccountActivity?ID=${cpr}&action=newaccount">Create New Account</a>
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
				LinkedList<Account> accounts = (LinkedList<Account>) request.getAttribute("accounts");
				for (Account acc : accounts) {%>
				<tr onclick="document.location = 'AccountActivity?ID=${cpr}&action=viewaccount&accountID=<%=acc.getAccountID()%>&accountName=<%=acc.getName()%>';">
					<td> <%= acc.getAccountID()%></td>
					<td> <%= acc.getName()%></td>
					<td> <%= acc.getBalanceString()%></td>
				</tr>
				<%}%>
			</table>
		</div>
	</div>
	</table>
</div>
<%@include file="footer.jsp"%>
</html>
