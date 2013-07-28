<%@page session = "true"%>
<%@page language="java" import = "java.sql.*"%> 
<%@page language="java" import = "java.io.*"%>
<jsp:useBean id="DbManager" class="org.lucas.servlet.DbManager" scope="session" />			<!-- Create an instance of class DbManager if it does not exist yet -->

<%
//Add a header to mark this page as not modified (to prevent IE from reloading images)
response.addHeader("Last-Modified", "Sat, 15 Oct 2005 05:00:00 GMT");
response.addHeader("Expires", "Sat, 15 Oct 2050 05:00:00 GMT");
response.addHeader("Cache-Control", "public");

String downloadNum = request.getParameter("downloadNum");
if((downloadNum != null) && (!downloadNum.equals("")))
{
	Object rsValue = null;
	HttpSession sessionlogin = request.getSession();
	String loginuser = (String) sessionlogin.getAttribute("loginuser");
	try {
		Statement stat = DbManager.createQueryStatement();
		ResultSet rs = DbManager.getQueryResult(stat, "SELECT A.LAST_MODIFIED LAST_MODIFIED, A.DESCRIPTION DESCRIPTION, A.DOWNLOAD_URL DOWNLOAD_URL FROM DOWNLOADS A WHERE DOWNLOAD_NUM = " + downloadNum);
		if(rs.next()) {
			%>
			<div class="boxhead">
				<h2>
				<table width="90%" border="0">
				<tr> 
				<td width="40%">Last modified: <%=(((rsValue = rs.getObject("LAST_MODIFIED"))==null || rs.wasNull())?"":rsValue)%></td>
				<td width="30%"><a href="<%=(((rsValue = rs.getObject("DOWNLOAD_URL"))==null || rs.wasNull())?"":rsValue)%>">Download</a></td>
				<% if((loginuser != null) && loginuser.equals("Webmaster")) { %>
					<td width="15%"><div class="outer"><a class="button" href="PostDownload.jsp?downloadNum=<%=downloadNum%>">Modify</a></div></td><td width="15%"><div class="outer"><a class="button" href="javascript:deleteRecord(<%=downloadNum%>)">Delete</a></div></td>
				<% } %>
				</tr>
				</table>
				</h2>
			</div>
			<div class="boxbody">
				<table width="90%" border="0">
				<tr> 
				<td width="20%"><img src="ImageLoader.jsp?downloadNum=<%=downloadNum%>";width="100%";height="100%";background="ffffff"/></td>
				<td width="80%"><%=(((rsValue = rs.getObject("DESCRIPTION"))==null || rs.wasNull())?"":((String) rsValue).replaceAll("\n", "<br>"))%></td>
				</tr>
				</table>
			</div>
			<%
		}		
		rs.close();
		stat.close();
	} catch (Exception e) {
		out.print("<body>ERROR:<br>");
		out.print(e + "</body>");
	}
}
%>
