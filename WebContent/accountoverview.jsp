<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Transaction" %>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<div class="pagetitle">
			<div class="col-sm-10">Activity for ${accountID}</div>
		</div>
		<div class="pagesubtitle2" align="right"><a href="Activity?ID=${cpr}&action=closeaccount&accountID=${accountID}">Close Account</a></div>
		<div align="center"><table>
		  		<col width="15%">
		  		<col width="50%">
		  		<col width="15%">
		  		<col width="20%">
            	<tr>
                	<th>Date</th>
                	<th>Category</th>
                	<th>Amount</th>
               		<th>Balance</th>
           		</tr>
           		<%
				LinkedList<Transaction> tran = (LinkedList<Transaction>) request.getAttribute("transactions");
				for (int i = 0; i < tran.size(); i++) {%>
				<tr>
					<td> <%= tran.get(i).getDate() %></td>
					<td> <%= tran.get(i).getName() %></td>
					<td> <%= tran.get(i).getAmmount() %></td>
					<td> </td>
				<tr />
				<%}%>
       		</table></div>	
	</div>
</div>
<%@include file="footer.jsp"%>
</html>