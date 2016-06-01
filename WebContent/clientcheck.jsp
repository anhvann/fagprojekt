<%@include file="header.jsp"%>
<%if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {
response.sendRedirect("login.jsp");
} else if (session.getAttribute("role").equals("e")){
response.sendRedirect("index.jsp");
}%>