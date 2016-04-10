package fagprojekt;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String submit = request.getParameter("loginButton");

		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}	

		Properties properties = new Properties();
		properties.put("user", "DTU12");
		properties.put("password", "FAGP2016");
		String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(url, properties);
			//statement = connection.createStatement();
			statement = connection.prepareStatement("select * from DTUGRP05.USERINFO where \"Email\"=? and \"Password\"=?");
			statement.setString(1, email);
			statement.setString(2, password);
			rs = statement.executeQuery();
			
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (submit != null) {
			System.out.print(email+" "+password);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
