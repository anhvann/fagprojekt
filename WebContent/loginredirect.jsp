<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<%if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {%>
	You are not logged in
<%} else {
	String role = (String)session.getAttribute("role");	
	if (role.equals("e")){
		response.sendRedirect("search.jsp");
	} else {
		response.sendRedirect("accounts");
	}
}%>
</body>
</html>