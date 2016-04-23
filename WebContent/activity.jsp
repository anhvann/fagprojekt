<!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Account" %>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div class="pagetitle">
			${fullname} (${cpr})
		</div>
		<div class="pagesubtitle">
			<a href="Activity?ID=${cpr}&action=edit">Edit User Information</a><br>
			<a href="Activity?ID=${cpr}&action=newaccount">Create New Account</a>
		</div>
		<div align="center">
			<table class="clickable">
			    <col width="70%">
		  		<col width="30%">
				<tr>
					<th>Account</th>
					<th>Balance</th>
				</tr>
				<%
				LinkedList<Account> acc = (LinkedList<Account>) request.getAttribute("accounts");
				for (int i = 0; i < acc.size(); i++) {%>
				<tr onclick="document.location = 'Activity?ID=${cpr}&action=viewaccount&accountID=<%=acc.get(i).getAccountID()%>';">
					<td> <%= acc.get(i).getAccountID() %></td>
					<td> <%= acc.get(i).getBalance() %></td>
				<tr />
				<%}%>
			</table>
		</div>
	</div>
	</table>
</div>
<%@include file="footer.jsp"%>
</html>
