<%@page language="java" import = "java.sql.*"%><%@page language="java" import = "java.io.*"%><jsp:useBean id="DbManager" class="org.lucas.servlet.DbManager" scope="session" /><%
//keep the servlet snippets without blank spaces/carriage returns inbetween, otherwise the returned datastram will include them

//Add a header to mark this page as not modified (to prevent IE from reloading images)
response.addHeader("Last-Modified", "Sat, 15 Oct 2005 05:00:00 GMT");
response.addHeader("Expires", "Sat, 15 Oct 2050 05:00:00 GMT");
response.addHeader("Cache-Control", "public");

String downloadNum = request.getParameter("downloadNum");
if((downloadNum != null) && (!downloadNum.equals("")))
{
	try {
		Statement stat = DbManager.createQueryStatement();
		ResultSet rs = DbManager.getQueryResult(stat, "SELECT A.DOWNLOAD_IMG DOWNLOAD_IMG FROM DOWNLOADS A WHERE DOWNLOAD_NUM = " + downloadNum);
		if(rs.next()) {
			InputStream iStream = rs.getBinaryStream("DOWNLOAD_IMG");
			response.setContentType("image/png");
		    response.setContentLength(iStream.available());
			OutputStream oStream = response.getOutputStream();
			
			int ch = 0;
		    while((ch = iStream.read()) != -1){
		        oStream.write(ch);
	        }
			oStream.flush(); 
			oStream.close();
			iStream.close();
		}		
		rs.close();
		stat.close();
	} catch (Exception e) {
		response.setContentType("text/plain");
		out.print("Image could not be loaded.");
		out.print(e);
	}
}
%>