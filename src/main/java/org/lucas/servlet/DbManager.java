package org.lucas.servlet;

import java.sql.*;


public class DbManager {
	private Connection conn = null;

			
	public void createConnection(String driverName, String connStringDb, String userNameDb, String userPwdDb) throws Exception {
		try{
			Class.forName(driverName).newInstance();
			conn = DriverManager.getConnection(connStringDb, userNameDb, userPwdDb);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			throw(new Exception("Connection to database >" + userNameDb + "< could not be established."));
		}
	}

	
	public Connection getConnection() {
		return conn;
	}

			
	public void closeConnection() {
		try{
			if(conn != null) {
				conn.close();
				conn = null;
			}
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	
	public Statement createQueryStatement() {
		Statement stat = null;
		
		try {
			if(conn != null)
				stat = conn.createStatement();
			else
				throw(new Exception("No connection exists."));
		}
		catch(Exception e) {
			stat = null;
			System.out.println("ERROR: " + e.getMessage());
		}
		return stat;		//return the statement
	}
	
	public ResultSet getQueryResult(Statement stat, String sqlStatement) {
		ResultSet rs = null;
		
		try {
			if((conn != null) && (stat != null))	
				rs = stat.executeQuery(sqlStatement);
			else
				throw(new Exception("No connection exists."));
		}
		catch(Exception e) {
			rs = null;
			System.out.println("ERROR: " + e.getMessage());
		}
		return rs;		//return the result set
	}
	
	public void executeStatement(String sqlStatement) {
		try {
			if(conn != null) {
				Statement stat = conn.createStatement();
				stat.execute(sqlStatement);
				stat.close();
			}
			else
				throw(new Exception("No connection exists."));
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}