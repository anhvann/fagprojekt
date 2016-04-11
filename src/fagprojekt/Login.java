package fagprojekt;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Properties;

import javax.management.relation.Role;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = "";

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
		Boolean validate = false;
		try {
			connection = DriverManager.getConnection(url, properties);
			statement = connection.prepareStatement("SELECT * FROM DTUGRP05.USERINFO WHERE \"Email\"=?");
			statement.setString(1, email);
			rs = statement.executeQuery();
			while (!validate && rs.next()) {
				if (rs.getString("Email").equals(email) && rs.getString("Password").equals(password)) {
					validate = true;
					role = rs.getString("Role");
				}
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validate) {
			if (role.equals("Employee")) {
				response.sendRedirect("search.jsp");
			} else {
				response.sendRedirect("activity.jsp");
			}
		} else {
			String message = "E-mail address or password was incorrect";
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
	}

}
