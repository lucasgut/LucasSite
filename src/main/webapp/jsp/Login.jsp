<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<link href="resources/css/MainStyles.css" type="text/css" rel="stylesheet">
	<title>Lucas' Programming Resources</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
	<%@ include file="Header.jsp" %>
	<table width="100%" height="84%" border="0" align="left" valign="top">
		<tr valign="top">
			<td class="tdMenuLeft" valign="top" width="10%">
				<%@ include file="Menu.jsp" %>
			</td>
			<td class="tdMainSection" valign="top" width="90%">
				<h3>Login</h3><br/>
				<form:form method="POST" modelAttribute="user" action="Login.html">
					<form:errors path="*" cssClass="errorblock" element="div"/>
					<table width="65%" border="0">
						<tr><td>User:</td><td><form:input path="userName" /></td></tr>
						<tr><td>Password:</td><td><form:input path="password" type="password" /></td></tr>
						<tr><td><input type="submit" value="Login" /></td></tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>