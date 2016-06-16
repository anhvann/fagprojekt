<!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.User"%>
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
<html>
<div id="wrap">
	<%@include file="employeecheck.jsp"%>
	<div class="main">
		<form id=searchbar action="Search" method="get" target="_self">
			<div style="overflow: hidden">
				<input id="searchfield" type="text" name="searchfield"
					placeholder="Search users by name, cpr or phone" style="width: 100%" />
			</div>
		</form>
		<div class="pagetitle">
			<font size="4"><font color="grey">${searchmessage}</font></font>
		</div>
		<br>
		<%
			LinkedList<User> users = (LinkedList<User>) request.getAttribute("resultlist");
			if (users != null) {
		%>
		<div align="center">
			<table class="clickable">
				<col width="15%">
				<col width="30%">
				<col width="30%">
				<col width="15%">
				<tr>
					<th>CPR</th>
					<th>Name</th>
					<th>Address</th>
					<th>Phone</th>
				</tr>
				<%
					for (User user : users) {
				%>
				<tr
					onclick="document.location = 'UserActivity?ID=<%=user.getCPR()%>&action=viewuser';">
					<td><%=user.getCPR()%></td>
					<td><%=user.getName()%></td>
					<td><%=user.getAddress()%></td>
					<td><%=user.getPhone()%></td>
				<tr />
				<%
					}
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

