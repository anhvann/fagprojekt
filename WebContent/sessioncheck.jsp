<%@include file="header.jsp"%>
<%if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {
response.sendRedirect("login.jsp");
}%>