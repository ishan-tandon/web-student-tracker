package com.itapp.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from student order by last_name");
			
			while(myRs.next()) {
				students.add(new Student(myRs.getInt("id"), myRs.getString("first_name"), myRs.getString("last_name"), myRs.getString("email")));
			}
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
		return students;		
		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
			
		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

	public void addStudent(Student newStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		String firstName = newStudent.getFirstName();
		String lastName = newStudent.getLastName();
		String email = newStudent.getEmail();
		
		String sql = "insert into student "
				+ "(first_name, last_name, email) "
				+ "values (?, ?, ?)";
				
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1,  firstName);
			myStmt.setString(2,  lastName);
			myStmt.setString(3,  email);
			
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception {

		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int studentId;
		
		String sql = "select * from student "
				+ "where id = ?";
				
		try {
			studentId = Integer.parseInt(theStudentId);
			
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1,  studentId);
			
			myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				theStudent = new Student(myRs.getInt("id"), myRs.getString("first_name"), myRs.getString("last_name"), myRs.getString("email"));
			}
			
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
		return theStudent;
		
		
	}

	public void updateStudent(Student theStudent) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		String firstName = theStudent.getFirstName();
		String lastName = theStudent.getLastName();
		String email = theStudent.getEmail();
		
		String sql = "update student "
				+ "set first_name=?, last_name=?, email=? "
				+ "where id=?";
				
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1,  firstName);
			myStmt.setString(2,  lastName);
			myStmt.setString(3,  email);
			myStmt.setInt(4,  theStudent.getId());
			
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}

	public void deleteStudent(int id) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		String sql = "delete from student "
				+ "where id=?";
				
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1,  id);
			
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}
}
