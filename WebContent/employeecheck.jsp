<%@include file="header.jsp"%>
<%if ((session.getAttribute("loggedinuser") == null) || (session.getAttribute("loggedinuser") == "")) {
response.sendRedirect("login.jsp");
} else if (session.getAttribute("role").equals("c")){
response.sendRedirect("index.jsp");
}%>