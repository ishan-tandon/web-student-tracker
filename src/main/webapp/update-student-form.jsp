<html>

<head>
	<title>Add Student</title>	
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Sample University</h2>
		</div>
	</div>
	
	<div id="container">
		<h2>Update Student</h2>
			
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="studentId" value="${the_student.id}" />
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>	
						<td><input type="text" name="firstName" value="${the_student.firstName}"/></td>
					</tr>
					<tr>
						<td><label>Last name:</label></td>	
						<td><input type="text" name="lastName" value="${the_student.lastName}"/></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>	
						<td><input type="text" name="email" value="${the_student.email}"/></td>
					</tr>
					<tr>
						<td><label></label></td>	
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
		
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="StudentControllerServlet">Back to home page</a>
		</p>
	</div>
</body>

</html>