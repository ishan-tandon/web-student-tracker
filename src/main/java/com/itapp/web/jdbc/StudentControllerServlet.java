package com.itapp.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch (Exception exc){
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String command = request.getParameter("command");
			
			if (command == null) {
				command = "LIST";
			}
			
			switch(command) {
			
			case "LIST":
				listStudents(request, response);
				break;
			
			case "ADD":
				addStudent(request, response);
				break;
			
			case "LOAD":
				loadStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default:
				listStudents(request, response);				
				
			}

		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("studentId"));
		
		studentDbUtil.deleteStudent(id);
		
		try {
			listStudents(request,response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);	
		}
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(id, firstName, lastName, email);
		
		studentDbUtil.updateStudent(theStudent);
		
		try {
			listStudents(request,response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);	
		}
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String studentId = request.getParameter("studentId");
		
		Student theStudent = studentDbUtil.getStudent(studentId);
		
		request.setAttribute("the_student", theStudent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Student newStudent = new Student(request.getParameter("firstName"),request.getParameter("lastName"),request.getParameter("email"));
		
		studentDbUtil.addStudent(newStudent);
		
		try {
			listStudents(request,response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);	
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Student> students = studentDbUtil.getStudents();
		
		request.setAttribute("student_list", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
	}

}
