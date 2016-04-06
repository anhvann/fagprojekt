package fagprojekt;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		System.out.println("Driver found");
//		String email = request.getParameter("email");
//		String password = request.getParameter("password");
//		String submit = request.getParameter("loginButton");
//		String url = "jdbc:db2:DALLASB";
		
//		Connection connection;
//		
//		try {
//			connection = DriverManager.getConnection("jdbc:db2://localhost:9080/Uniccol Bank", "DTU12", "FAGP2016");
//		} catch (SQLException e) {
//			System.out.println("Connection could not be established");
//		}
//		PreparedStatement pStatement = null;;
//		ResultSet resultSet = null;
//		boolean found = false;
		
		
		
		
//		if (submit != null) {
//			System.out.print(email+" "+password);
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
