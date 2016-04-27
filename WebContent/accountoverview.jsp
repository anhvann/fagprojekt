<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Transaction" %>

<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<div class="pagetitle">Activity for ${accountID}</div>
		<hr width="95%" noshade>
		<div class="pagesubtitle2" align="right">
			<a href="Activity?ID=${cpr}&action=closeaccount&accountID=${accountID}">Close Account</a>
			<br>
			<a href="Activity?ID=${cpr}&action=editaccount&accountID=${accountID}">Edit Account</a>
		</div>
		
		<div align="center">
			<table class="clickable">
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
				LinkedList<Transaction> trans = (LinkedList<Transaction>) request.getAttribute("transactions");
				for (int i = 0; i < trans.size(); i++) {%>
					<td> <%= trans.get(i).getAccountID() %></td>
					<td> <%= trans.get(i).getName() %></td>
					<td> <%= trans.get(i).getAmount() %></td>
				<%}%>
			</table>
		</div>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>