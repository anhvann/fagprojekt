<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Transaction"%>
<%@ page import="java.math.BigDecimal"%>
<html>
<link rel="stylesheet" type="text/css" href="Style.css">
<script>
	function toast() {
		var x = document.getElementById("successtoast")
		x.className = "show";
		setTimeout(function() {
			x.className = x.className.replace("show", "");
		}, 3000);
	}
</script>
<div id="wrap">
		<%@include file="sessioncheck.jsp"%>
		<div class="main">
			<div class="pagetitle">${accountName} (${accountID})</div>
			<hr width="95%" noshade>
			<div align="center">
				<div class="content">
					<p style="text-align: left;">
						<font size="5"><font color="#01405b">Balance <span
								style="float: right;"><%=request.getAttribute("balance")%>
									<%=request.getAttribute("ISOCode")%></span></font></font> <br>
						<%
							if (session.getAttribute("role").equals("e")) {
						%>
					
					<div align="right">
						<font size="2" color="red">${errormessage}</font> <br> <span
							style="float: left;"> <a
							href="UserActivity?ID=${cpr}&action=viewuser">Back to
								accounts</a>
						</span> <span style="float: right;"> <a
							href="AccountActivity?ID=${cpr}&action=editaccount&accountID=${accountID}">Edit
								Account</a> |
							<form input style="display: inline;"
								action="AccountActivity?ID=${cpr}&action=closeaccount&accountID=${accountID}"
								method="post">
								<button type="submit" name="delete" " class="btn-link">Close
									Account</button>
							</form> | <a
							href="AccountActivity?ID=${cpr}&action=addowner&accountID=${accountID}">Add
								Owner</a>
								| <a href="AccountActivity?ID=${cpr}&action=removeowner&accountID=${accountID}">
								Remove owner(s)</a>
						</span>
					</div>
					<%
						}
					%>
					<br>
					</p>
				</div>

				<table>
					<col width="20%">
					<col width="40%">
					<col width="20%">
					<col width="20%">
					<tr>
						<th>Date</th>
						<th>Transaction name</th>
						<th>Amount</th>
						<th>Balance</th>
					</tr>
					<%
						String color;
						LinkedList<Transaction> trans = (LinkedList<Transaction>) request.getAttribute("transactions");
						for (int i = trans.size() - 1; i >= 0; i--) {
							if (trans.get(i).getAmount().compareTo(BigDecimal.ZERO) > 0) {
								color = "green";
							} else {
								color = "red";
							}
					%>
					<tr>
						<td><%=trans.get(i).getDate()%></td>
						<td><%=trans.get(i).getName()%></td>
						<td><font color="<%=color%>"><b><%=trans.get(i).getAmountString()%></b></font></td>
						<td><b><%=trans.get(i).getBalanceString()%></b></td>
					</tr>
					<%
						}
					%>
				</table>
				<br><br><br>
			</div>
			<div id="successtoast">${message}</div>
			<script>
				if (
			<%=request.getAttribute("toast")%>
				) {
					toast();
				}
			</script>
		</div>
	</div>
<%@include file="footer.jsp"%>
</html>