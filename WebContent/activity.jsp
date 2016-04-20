<!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Account" %>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div id="message">
			<font size="4"><font color="grey">${fullname} (${cpr})</font></font>
		</div>
		<div align="center">
			<table>
				<tr>
					<th>Account</th>
					<th>Balance</th>
				</tr>
				<%
				LinkedList<Account> acc = (LinkedList<Account>) request.getAttribute("accounts");
				for (int i = 0; i < acc.size(); i++) {%>
				<tr onclick="document.location = 'activity.jsp';">
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