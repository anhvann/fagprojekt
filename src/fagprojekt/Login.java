package fagprojekt;

import Controller.BankApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

	private static Connection db2Conn;
	private static Statement stmt;
	BankApp bankApp = null;

	public Login() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	// public void init(ServletConfig config) throws ServletException {
	// String DB_USER = "DTU11";
	// String DB_PASSWORD = "FAGP2016";
	// try {
	// Class.forName("com.ibm.db2.jcc.DB2Driver");
	// db2Conn =
	// DriverManager.getConnection("jdbc:db2://192.86.32.54:5040/DALLASB:" +
	// "user=" + DB_USER + ";"
	// + "password=" + DB_PASSWORD + ";");
	// } catch (SQLException | ClassNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (email.isEmpty() || password.isEmpty()) {
			String message = "Please fill in all fields";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		if (bankApp == null) {
			bankApp = new BankApp();
		}
		try {
			String role = bankApp.getRole(email, password);
			if (role.equals("employee")) {
				response.sendRedirect("search.jsp");
			} else if (role.equals("client")) {
				response.sendRedirect("activity.jsp");
			} else {
				String message = "E-mail address or password was incorrect";
				request.setAttribute("message", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {
		RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher(url);
		System.out.println(RequetsDispatcherObj != null);
		System.out.println(request != null);
		System.out.println(response != null);
		RequetsDispatcherObj.forward(request, response);
	}
}
