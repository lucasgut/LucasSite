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
				<h3>Home</h3><br/>
				<form:form method="POST" modelAttribute="upload" action="PostDownload.html" enctype="multipart/form-data">
					<form:errors path="*" cssClass="errorblock" element="div"/>
					<table width="65%" border="0">
						<tr><td>Id:</td><td><form:input path="id" type="number" /></td></tr>
						<tr><td>Subject:</td><td><form:input path="subject" /></td><td><form:errors path="subject" cssClass="error" /></td></tr>
						<tr><td>Description:</td><td><form:textarea path="description" cols="46" rows="10" /></td></tr>
						<tr><td>File:</td><td><form:input path="file" type="file" size="60" /></td></tr>
						<tr><td>URL:</td><td><form:input path="url" size="60" /></td></tr>
						<tr><td>Image:</td><td><form:input path="image" type="file" accept="image/jpeg" /></td></tr>
						<tr><td valign="top">&nbsp; </td><td><input type="submit" name="Submit" value="Submit"><input type="reset" name="Reset" value="Reset"></td></tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>