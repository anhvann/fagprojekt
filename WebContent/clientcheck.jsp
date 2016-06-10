<%@include file="header.jsp"%>
<%if ((session.getAttribute("loggedinuser") == null)) {
response.sendRedirect("login.jsp");
} else if (session.getAttribute("role").equals("e") || !session.getAttribute("loggedinuser").equals(request.getParameter("ID"))){
response.sendRedirect("index.jsp");
}%>

