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
		<div class="pagesubtitle2">
			<a href="Activity?ID=${cpr}&action=closeaccount&accountID=${accountID}">Close Account</a>
			<br>
			<a href="Activity?ID=${cpr}&action=editaccount&accountID=${accountID}">Edit Account</a>
			<br>
			<a href="Activity?ID=${cpr}&action=deposit&accountID=${accountID}">Deposit Money</a>
			<br>
			<a href="Activity?ID=${cpr}&action=withdraw&accountID=${accountID}">Withdraw Money</a>
			<br>
			<a href="Activity?ID=${cpr}&action=transfer&accountID=${accountID}">Transfer Money</a>
		</div>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>