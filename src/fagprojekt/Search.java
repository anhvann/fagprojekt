package fagprojekt;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
