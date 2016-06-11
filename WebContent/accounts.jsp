<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="Style.css">
<script>
	function toast() {
	    var x = document.getElementById("successtoast")
	    x.className = "show";
	    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
	}
</script>
<html>
<div id="wrap">
<div class="container">
	<%@include file="sessioncheck.jsp" %>
	<%@ page import="java.sql.*"%>
	<%@ page import="java.util.LinkedList"%>
	<%@ page import="model.Account" %>
	<div class="main">
		<div class="pagetitle">	${name} (${cpr})</div>
		<hr width="95%" noshade>
		<div align="center">
			<div class="content">
			<p style="text-align:left;">
			<%if (session.getAttribute("role").equals("e")){ %>
				<div align="left">
					<font size="2" color="red">${errormessage}</font>
					<br>
					<font size="2"></font>
					<span style="float:left;">
						<a href="UserActivity?ID=${cpr}&action=edit">Edit User Information</a> | 
						<form input style="display: inline;" action="UserActivity?ID=${cpr}&action=delete" method="post">
  							<button type="submit" name="delete"" class="btn-link">Delete User</button>
						</form> 
					</span>
					<span style="float:right;">
						<a href="AccountActivity?ID=${cpr}&action=newaccount">Create New Account</a>
					</span>
				</div>
			<%}%>
			<br>
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
				<tr onclick="document.location = 'AccountActivity?ID=${cpr}&action=viewaccount&accountID=<%=acc.getAccountID()%>';">
					<td> <%= acc.getAccountID()%></td>
					<td> <%= acc.getName()%></td>
					<td> <%= acc.getBalanceString()%></td>
				</tr>
				<%}%>
			</table>
		</div>
	</div>
	</table>
	<div id="successtoast">${message}</div>
    	<script>
    		if (<%=request.getAttribute("toast")%>) {
   				toast();
    		}
    	</script>
</div>
</div>
<%@include file="footer.jsp"%>
</html>
