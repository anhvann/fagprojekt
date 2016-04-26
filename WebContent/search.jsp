<!DOCTYPE html>
<%@ page import="java.sql.*" %>
<% Class.forName("com.ibm.db2.jcc.DB2Driver"); %>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main">
		<form id=searchbar action="Search" method="post" target="_self">
			<input id=searchbutton type="submit" value="Search"	style="float: right" />
			<div style="overflow: hidden">
				<input id="searchfield" type="text" name="searchfield"
					placeholder="Search by name, cpr or account number"
					style="width: 100%" />
			</div>
		</form>
		<div class="pagetitle"><font size="4"><font color="grey">${message}</font></font></div><br>
		<%
			Connection connection = DriverManager.getConnection("jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;", "DTU12", "FAGP2016");
            Statement statement = connection.createStatement() ;
		 	String sqlStatement = (String) request.getAttribute("resultlist"); 
  			if (sqlStatement != null){
            ResultSet resultset = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" IN "+sqlStatement+" ");%>
            <div align="center"><table class="clickable">
            	<col width="25%">
		  		<col width="30%">
		  		<col width="30%">
		  		<col width="15%">
            	<tr>
                	<th>ID</th>
                	<th>Name</th>
                	<th>Address</th>
               		<th>Phone</th>
           		</tr>
           		<% while(resultset.next()){ %>
           		<tr onclick="document.location = 'Activity?ID=<%=resultset.getString("CPRNo")%>&action=viewuser';">
               		<td> <%= resultset.getString("CPRNo") %></td>
               		<td> <%= resultset.getString("FullName") %></td>
               		<td> <%= resultset.getString("Address") %></td>
               		<td> <%= resultset.getString("Phone") %></td>
           		<tr/>
           		<% } %>
           		
       		</table></div>
       		<% } %>	
	</div>
	<%@include file="footer.jsp"%>
</html>

