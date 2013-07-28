<%@ page session = "true" %>

<html>
<head>
<link href = "MainStyles.css" type = "text/css" rel = "stylesheet">
<title>Lucas' Programming Resources</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head> 
<body>
<%
HttpSession sessionLogin = request.getSession();
String passwd = request.getParameter("passwd");
if((passwd != null) && (passwd.equals("123456") == true)) {
	sessionLogin.setAttribute("loginuser", "Webmaster");
%>
	<br><h3>Login successful!</h3><br>
<% 
} else { 
	sessionLogin.removeAttribute("loginuser");
	if(passwd != null) { %>
		<br><h3>Login failed!</h3>
	<% } %>
	<br><br>
	<table id = logintable>
	<form action="CheckLogin.jsp" method="post">
	<tr>
	<td align="right">Password:</td><td>&nbsp;</td>
	<td><input type="password" name="passwd" size="20"></td>
	<td><input type="submit" value="Login"></td>
	</tr>
	</form>
	</table>
<% } %>
<a href="index.jsp">Back</a>
</body>
