<%@include file="header.jsp"%>
<%if ((session.getAttribute("loggedinuser") == null) 
|| (session.getAttribute("role").equals("c") && !session.getAttribute("loggedinuser").equals(request.getParameter("ID")))) {
response.sendRedirect("login.jsp");
}
%>