<!DOCTYPE HTML>
<body>
Logging out
<%session.setAttribute("loggedinuser", null);
session.invalidate();
response.sendRedirect("index.jsp");
%>

</body>
</html>