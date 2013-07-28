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
<% 	
String currentSection = request.getParameter("Section");
HttpSession sessionLogin = request.getSession();
//Check whether this session has just been created
if(sessionLogin.isNew() || DbManager.getConnection() == null) {
	System.out.println("Connecting to database...");
	try {
		DbManager.createConnection("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.99:3306/lucasgg", "lucasgg", "123456");		//Connect to database
		System.out.println("Connected. Load site configuation...");
	
		//Load the configuration data
		Statement stat = DbManager.createQueryStatement();
		ResultSet rs = DbManager.getQueryResult(stat, "SELECT A.CONFIG_KEY CONFIG_KEY, A.CONFIG_VALUE CONFIG_VALUE FROM CONFIG_DATA A");
		while(rs.next()) {
			sessionLogin.setAttribute((String) rs.getObject("CONFIG_KEY"), rs.getObject("CONFIG_VALUE"));
		}
		rs.close();
		stat.close();
	
		String sNumHits = Integer.toString(Integer.valueOf((String) sessionLogin.getAttribute("HIT_COUNTER")).intValue() + 1);
		sessionLogin.setAttribute("HIT_COUNTER", sNumHits);
		DbManager.executeStatement("UPDATE CONFIG_DATA SET CONFIG_VALUE = " + sNumHits + " WHERE CONFIG_KEY = 'HIT_COUNTER'");
		System.out.println("Done. Render page...");
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("ERROR: " + e.getMessage());
		out.print("ERROR:<br>" + e.getMessage() + "<br>");
	}
}
if(DbManager.getConnection() != null) {
%>
<img src="res/headLogo.gif">
<table width="100%" height="10%" border="0">
<tr colspan="2"> 
<td width="50%" >Visitor no.: <%=sessionLogin.getAttribute("HIT_COUNTER")%><br>
<% if((sessionLogin.getAttribute("loginuser") != null) && (!sessionLogin.getAttribute("loginuser").equals(""))) { %>
User:  <%=sessionLogin.getAttribute("loginuser")%> &nbsp;<a href="CheckLogin.jsp">Sign-out</a><br>
<% }else{ %>
User:  Guest &nbsp;<a href="CheckLogin.jsp">Sign-in</a><br>
<% } %>
</td>
</tr>
</table>
<table width="100%" height="84%" border="0" align="left" valign="top">
<tr valign="top" colspan="2"> 
<td class="tdMenuLeft" valign="top" width="10%">
<a class="aMenuLeft" href="index.jsp?Section=Home.jsp">Home</a><br>
<a class="aMenuLeft" href="index.jsp?Section=Downloads.jsp">Downloads</a><br>
</td>
<%
if(currentSection == null) {		//Start a home.jsp
%>
<td class="tdMainSection" valign="top" width="90%"><jsp:include page="Home.jsp"/></td>
<% } else { %>
<td class="tdMainSection" valign="top" width="90%"><jsp:include page="<%=currentSection%>"/></td>
<% } %>
</tr>
</table>
<% } %>
</body>
</html>

