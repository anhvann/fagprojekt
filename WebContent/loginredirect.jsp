<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<body>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Account" %>
<%if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {%>
	You are not logged in
<%} else {
	String role = (String)session.getAttribute("role");
	if (role.equals("e")){
		response.sendRedirect("search.jsp");
	} else {
		LinkedList<Account> accounts = (LinkedList<Account>) request.getAttribute("accounts");
		String name = (String) request.getAttribute("fullname");
		request.setAttribute("accounts", accounts);
		request.setAttribute("fullname", name);
		request.setAttribute("cpr", session.getAttribute("loggedinuser"));
		request.getRequestDispatcher("accounts.jsp").forward(request, response);
	}
}%>
</body>
</html>