<%@page session = "true"%>
<%@page language="java" import = "java.io.*"%>
<%@page language="java" import = "java.sql.*"%>
<jsp:useBean id="DbManager" class="org.lucas.servlet.DbManager" scope="session" />			<!-- Create an instance of class DbManager if it does not exist yet -->

<html>
<head>
<link href = "MainStyles.css" type = "text/css" rel = "stylesheet">
<title>Lucas' Programming Resources</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head> 
<body>
<h3>Download:<br>
</h3>

<%
Object rsValue = null;
String downloadNum = request.getParameter("downloadNum");
String subject = "";
String description = "";
String DbAction = "Insert";
if(downloadNum != null) {		//Modify an existing download
	try {
		Statement stat = DbManager.createQueryStatement();
		ResultSet rs = DbManager.getQueryResult(stat, "SELECT A.SUBJECT SUBJECT, A.DESCRIPTION DESCRIPTION, A.DOWNLOAD_URL DOWNLOAD_URL FROM DOWNLOADS A WHERE A.DOWNLOAD_NUM = '" + downloadNum + "'");
		if(rs.next()) { 
			subject = (String) (((rsValue = rs.getObject("SUBJECT"))==null || rs.wasNull()) ? "" : rsValue);
			description = (String) (((rsValue = rs.getObject("DESCRIPTION"))==null || rs.wasNull()) ? "" : rsValue);
			DbAction = "Update";
		}
		else
			downloadNum = null;

		rs.close();
		stat.close();
	} catch (Exception e) {
		out.print("ERROR: " + e + "<br>");
	}
}
%>

<form name="formPostDownload" enctype="multipart/form-data" method="post" action="/DownloadsServlet">
<input type="HIDDEN" name="DOWNLOAD_NUM" VALUE="<%=downloadNum%>">
<input type="HIDDEN" name="DB_ACTION" value="<%=DbAction%>">
<table width="65%" border="0">
<tr> 
<td>Subject:</td>
<td> 
<input type="text" name="SUBJECT" size="50" value="<%=subject%>">
</td>
</tr>
<tr> 
<td valign="top">Description:</td>
<td>
<textarea name="DESCRIPTION" cols="46" rows="10"><%=description%></textarea>
</td>
</tr>
<tr> 
<td>Download URL/File:</td>
<td> 
<input type="FILE" NAME="DOWNLOAD_FILE" size="60">
</td>
</tr>
<tr> 
<td>Image:</td>
<td> 
<input type="FILE" NAME="DOWNLOAD_IMG" size="60">
</td>
</tr>
<tr> 
<td valign="top">&nbsp; </td>
<td>
<input type="submit" name="Submit" value="Submit">
<input type="reset" name="Reset" value="Reset">
</td>
</tr>
</table>
<a href="index.jsp?Section=Downloads.jsp">Back</a> 
</form>
</body>
</html>

