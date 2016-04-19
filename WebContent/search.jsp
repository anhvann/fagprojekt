<!DOCTYPE html>
<%@ page import="java.sql.*" %>
<% Class.forName("com.ibm.db2.jcc.DB2Driver"); %>
<html>
<div class="container">
	<%@include file="header.jsp"%>
	<div class="main">
		<form id=searchbar action="Search" method="post" target="_self">
			<input id=searchbutton type="submit" value="Search"
				style="float: right" />
			<div style="overflow: hidden">
				<input id="searchfield" type="text" name="searchfield"
					placeholder="Search by name, cpr or account number"
					style="width: 100%" />
			</div>
		</form>
		<div id="message"><font size="4"><font color="grey">${message}</font></font></div>
		
		<%
			Connection connection = DriverManager.getConnection("jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;", "DTU12", "FAGP2016");
            Statement statement = connection.createStatement() ;
		 	String sqlStatement = (String) request.getAttribute("resultlist"); 
  			if (sqlStatement != null){
            ResultSet resultset = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"UserID\" IN "+sqlStatement+" ");%>
            <div align="center"><table>
            	<tr>
                	<th>ID</th>
                	<th>Name</th>
                	<th>Address</th>
               		<th>Phone</th>
           		</tr>
           		<% while(resultset.next()){ %>
           		<tr onclick="document.location = 'activity.jsp';">
               		<td> <%= resultset.getString("UserID") %></td>
               		<td> <%= resultset.getString("UserName") %></td>
               		<td> <%= resultset.getString("Address") %></td>
               		<td> <%= resultset.getString("Phone") %></td>
           		<tr/>
           		<% } %>
       		</table></div>
       		<% } %>	
	</div>
	<%@include file="footer.jsp"%>
</html>
