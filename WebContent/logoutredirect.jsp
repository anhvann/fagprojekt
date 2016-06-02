<!DOCTYPE HTML>
<body>
Logging out
<%response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
response.setHeader("Pragma", "no-cache"); 
session.setAttribute("loggedinuser", null);
session.invalidate();
response.sendRedirect("index.jsp");
%>
</body>
</html>