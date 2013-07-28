<%@page session = "true"%>
<%@page language="java" import = "java.sql.*"%> 
<%@page language="java" import = "java.io.*"%>
<jsp:useBean id="DbManager" class="org.lucas.servlet.DbManager" scope="session" />			<!-- Create an instance of class DbManager if it does not exist yet -->

<%
Object rsValue = null;
HttpSession sessionlogin = request.getSession();
String loginuser = (String) sessionlogin.getAttribute("loginuser");
try {
	Statement stat = DbManager.createQueryStatement();
	ResultSet rs = DbManager.getQueryResult(stat, "SELECT A.DOWNLOAD_NUM DOWNLOAD_NUM, A.SUBJECT SUBJECT FROM DOWNLOADS A");
%>
	<html>
    <head>
        <link href = "MainStyles.css" type = "text/css" rel = "stylesheet">
		<script src = "scripts/ajax.js" type="text/javascript"></script>
        <script src = "scripts/downloads.js" type = "text/javascript"></script>
        <title>Lucas' Programming Resources</title>
		<meta http-equiv="Content-Type" content="text/html; charset=">
    </head>
    <body>
	<h3>Downloads <br>
	</h3>
	<% if((loginuser != null) && loginuser.equals("Webmaster")) { %>
		<a href="PostDownload.jsp">Post a download</a><br>
	<% } %>	
	<br>
	<form name="formDownloads" method="post" action="/DownloadsServlet">
	<input type="HIDDEN" name="DOWNLOAD_NUM" VALUE="">
	<input type="HIDDEN" name="DB_ACTION" VALUE="">
	<% 
	Long downloadNum;
	while (rs.next()) { 
		downloadNum = (Long) rs.getObject("DOWNLOAD_NUM"); %>
        <p>
        <a class = "heading" href = "javascript: toggleDiv('div_download_<%=downloadNum%>','download_content.jsp?downloadNum=<%=downloadNum%>')"><b><%=(((rsValue = rs.getObject("SUBJECT"))==null || rs.wasNull())?"":rsValue)%></b></a>
        </p>
	    <div class = "widthlessbox" id = "div_download_<%=downloadNum%>"></div>
	<% } %>
	</form>
   	</body>
	</html>

<%
	rs.close();
	stat.close();
} catch (Exception e) {
	out.print("<body>ERROR:<br>");
	out.print(e + "</body>");
}
%>
