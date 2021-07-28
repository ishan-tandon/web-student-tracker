package com.itapp.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/web_student_tracker") 
	private DataSource dataSource;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain");
		
		PrintWriter out = response.getWriter();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from student");
			
			while(myRs.next()) {
				out.println(myRs.getString("first_name") + " " + myRs.getString("last_name") + " " + myRs.getString("email"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

}
