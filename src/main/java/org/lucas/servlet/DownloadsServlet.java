package org.lucas.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import http.utils.multipartrequest.ServletMultipartRequest;
import http.utils.multipartrequest.MultipartRequest;


public class DownloadsServlet extends HttpServlet {
	private static final long serialVersionUID = 7209365247397447265L;
	private DbManager managerDB;
	private Connection conn = null;
	private Statement stat = null;
	private PreparedStatement pstat = null;
	private String sqlCompactStatement;
	

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sqlCompactStatement = config.getInitParameter("sqlCompactStatement");
	}
		
	public String escape_Chars(String str) {
		return str.replaceAll("'", "\\\\'");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		
		String SQL, DbAction, downloadNum, subject, description, downloadUrl, rootURL;
		String deleteFileName = null;
		boolean updateOK = false;
		InputStream inStreamImg = null;
		InputStream inStreamFile = null;
		MultipartRequest parser = null;

		//top html-tags here:
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Lucas' Programming Resources</title>");
		out.println("</head>");
		
		rootURL = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
		try
		{
			managerDB = (DbManager) req.getSession(false).getAttribute("DbManager");		//retrieve the java bean with instance ID "DbManager"
			conn = managerDB.getConnection();		//retrieve the current database connection

			if(req.getContentType().indexOf("multipart/form-data") >= 0) {
				parser = new ServletMultipartRequest(req,MultipartRequest.MAX_READ_BYTES,MultipartRequest.IGNORE_FILES_IF_MAX_BYES_EXCEEDED,null);
				DbAction = parser.getURLParameter("DB_ACTION");
			}
			else
				DbAction = req.getParameter("DB_ACTION");
			
			SQL = "";
			if((DbAction != null) && (!DbAction.equals(""))) {
				if((DbAction.equals("Insert")) || (DbAction.equals("Update")))				//*** INSERT or UPDATE a download***
				{
					subject = escape_Chars(parser.getURLParameter("SUBJECT"));
					description = escape_Chars(parser.getURLParameter("DESCRIPTION"));
					if(subject.equals("")) {
						out.println("<body bgcolor=\"#D1D4DF\" text=\"#173F5F\">");
						out.println("<br><h3>Please specify a subject.</h3><br>");
						out.println("<a href=\"" + rootURL + "PostDownload.html\">Back</a></body></html>");
						out.close();
						return; 
					}
				
					try {
						//obtain the record to be modified and delete its download file if it exists on this host
						downloadNum = "";
						if(DbAction.equals("Update")) {
							downloadNum = parser.getURLParameter("DOWNLOAD_NUM");
							
							//delete the associated file if it exists
							String downloadPath;
							stat = conn.createStatement();
							ResultSet rs = stat.executeQuery("SELECT A.DOWNLOAD_URL DOWNLOAD_URL FROM DOWNLOADS A WHERE DOWNLOAD_NUM = '" + downloadNum + "'");
							if(rs.next()) {
								downloadPath = (String) rs.getObject("DOWNLOAD_URL");
								int indx = downloadPath.lastIndexOf('/');
								if((indx >= 0) && (indx < downloadPath.length()-1)) {
									deleteFileName = downloadPath.substring(indx+1);
									downloadPath = getServletContext().getRealPath("/downloads/") + File.separator + deleteFileName;
									File f = new File(downloadPath);
									if(f.isFile())
										f.delete();
									else
										deleteFileName = null;		//file has not been deleted
								}
							}		
							rs.close();
							stat.close();
						}
						
						
						//retrieve the download URL and if a file has been transfered, save this one to disk
						inStreamFile = parser.getFileContents("DOWNLOAD_FILE");
						if((inStreamFile == null) || (inStreamFile.available() == 0)) {
							downloadUrl = parser.getRawFilename("DOWNLOAD_FILE");
							if(downloadUrl == null)
								downloadUrl = "";
						}
						else {
							// Save the uploaded file to disk
							downloadUrl = rootURL + "downloads/" +  parser.getBaseFilename("DOWNLOAD_FILE");	
							String downloadPath = getServletContext().getRealPath("/downloads/");
							BufferedInputStream inStreamFileBuf = new BufferedInputStream(inStreamFile);
							FileOutputStream file = new FileOutputStream(new File(downloadPath, parser.getBaseFilename("DOWNLOAD_FILE")));
							int read;
							byte[] buffer = new byte[4096];
							while ( (read = inStreamFileBuf.read(buffer)) != -1)
							{
								file.write(buffer, 0, read);
							}
							file.close();
							inStreamFileBuf.close();
							inStreamFile.close();
						}
						
						//create the SQL statement
						Calendar today = Calendar.getInstance();
						String sToday = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH)+1) + "-" + today.get(Calendar.DAY_OF_MONTH);
						if(DbAction.equals("Insert"))
							SQL = "INSERT INTO DOWNLOADS(SUBJECT, LAST_MODIFIED, DESCRIPTION, DOWNLOAD_URL, DOWNLOAD_IMG) VALUES ('" + subject + "', '" + sToday + "', '" + description + "', '" + downloadUrl + "', ?)";
						else if(DbAction.equals("Update"))
							SQL = "UPDATE DOWNLOADS SET SUBJECT = '" + subject + "', LAST_MODIFIED = '" + sToday + "', DESCRIPTION = '" + description + "', DOWNLOAD_URL = '" + downloadUrl + "', DOWNLOAD_IMG = ? WHERE DOWNLOAD_NUM = '" + downloadNum + "'";
						else
							throw new Exception("Undefined DB Action.");
												
						//retrieve the uploaded image and update database						
						pstat = conn.prepareStatement(SQL); 
						inStreamImg = parser.getFileContents("DOWNLOAD_IMG");
						if(inStreamImg == null)
							pstat.setNull(1, java.sql.Types.BINARY);
						else
							pstat.setBinaryStream(1, inStreamImg, inStreamImg.available());
							
						pstat.execute();
						updateOK = true;
					} catch (Exception e) {
						out.println("<br>ERROR:<br>");
						out.print(e.getMessage());
					} finally {
						if(inStreamImg != null)
							inStreamImg.close();
						if(pstat != null) {
							try {
								pstat.close();
							} catch (SQLException s) {
								out.println("<br>ERROR: Unable to close sql statement.<br>");
							}
						}
					}
				}
				else if(DbAction.equals("Delete"))												//*** DELETE a download ***
				{
					downloadNum = req.getParameter("DOWNLOAD_NUM");
					
					SQL = "DELETE FROM DOWNLOADS WHERE DOWNLOAD_NUM = '" + downloadNum + "'";
					try {
						//delete the associated file if it exists
						String downloadPath;
						stat = conn.createStatement();
						ResultSet rs = stat.executeQuery("SELECT A.DOWNLOAD_URL DOWNLOAD_URL FROM DOWNLOADS A WHERE DOWNLOAD_NUM = '" + downloadNum + "'");
						if(rs.next()) {
							downloadPath = (String) rs.getObject("DOWNLOAD_URL");
							int indx = downloadPath.lastIndexOf('/');
							if((indx >= 0) && (indx < downloadPath.length()-1)) {
								deleteFileName = downloadPath.substring(indx+1);
								downloadPath = getServletContext().getRealPath("/downloads/") + File.separator + deleteFileName;
								File f = new File(downloadPath);
								if(f.isFile())
									f.delete();
								else
									deleteFileName = null;		//file has not been deleted
							}
						}		
						rs.close();
						stat.close();

						//update database
						stat = conn.createStatement();
						stat.execute(SQL);
						stat.close(); 
						updateOK = true;
					} catch (Exception s) {
						out.println("<br>ERROR:<br>");
						out.print(s);
					} finally {
						if(stat != null) {
							try {
								stat.close();
							} catch (SQLException s) {
								out.println("<br>ERROR: Unable to close sql statement.<br>");
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(out);
		}
			

		if(updateOK) {
			//Compact database
			if((sqlCompactStatement != null) && (!sqlCompactStatement.equals(""))) {
				try {
					stat = conn.createStatement();
					stat.execute(sqlCompactStatement);
				} catch (SQLException s) {
					out.println("<br>ERROR: Unable to compact database using sql statement: " + sqlCompactStatement + ".<br>");
				} finally {
					if(stat != null) {
						try {
							stat.close();
						} catch (SQLException s) {
							out.println("<br>ERROR: Unable to close sql statement.<br>");
						}
					}
				}
			}
			out.println("<body bgcolor=\"#D1D4DF\" text=\"#173F5F\">");
			out.println("<p>&nbsp;</p>");
			out.println("<h3>Database has been updated successfully.<br></h3>");
			if(deleteFileName != null)
				out.println("<h3>Download file " + deleteFileName + " has been deleted.<br></h3><br>");
			
			out.println("<a href=\"" + rootURL + "index.jsp?Section=Downloads.jsp\">Back</a>");
			out.println("</body>");
			out.println("</html>");
		}
		else {
			out.println("<body bgcolor=\"#D1D4DF\" text=\"#173F5F\">");
			out.println("<p>&nbsp;</p>");
			out.println("<h3>ERROR:  Database could not be updated.<br>");
			out.println("</h3>");
			out.println("<a href=\"" + rootURL + "index.jsp?Section=Downloads.jsp\">Back</a>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}




