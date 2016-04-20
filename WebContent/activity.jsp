<!DOCTYPE html>
<%@ page import="java.sql.*"%>
<%
	Class.forName("com.ibm.db2.jcc.DB2Driver");
%>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<%
			Connection connection = DriverManager.getConnection(
					"jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;",
					"DTU12", "FAGP2016");
			Statement statement = connection.createStatement();
			String userID = request.getParameter("ID");
			String name = "";
			if (userID != null) {
				ResultSet resultset = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + userID + "' ");
				while (resultset.next()) {
					name = resultset.getString("FullName");
				}
				resultset = statement.executeQuery("select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = '"  + userID + "' ");
		%>
		<div id="message">
			<font size="4"><font color="grey"><%=name%> (<%=userID%>)</font></font>
		</div>

		<div align="center">
			<table>
				<tr>
					<th>Account</th>
					<th>Balance</th>
				</tr>
				<%while (resultset.next()) {%>
				<tr onclick="document.location = 'activity.jsp?ID=<%=resultset.getString("AccID")%>';">
					<td> <%= resultset.getString("AccID") %></td>
					<td> <%= resultset.getString("Balance") %></td>
				<tr />
				<%}%>
			</table>
		</div>
		<%
			}
		%>
	</div>
	</table>
</div>
<%@include file="footer.jsp"%>
</html>