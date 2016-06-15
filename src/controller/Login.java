package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Database;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String loggedinuser, role;
	Database db = null;
	String message;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		String cpr = request.getParameter("cpr");
		String password = request.getParameter("password");

		if (cpr.length() != 10) {
			message = "Given CPR number is invalid";
			request.setAttribute("errormessage", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {

			try {
				db = new Database(request.getSession());
				String role = db.Login(cpr, password);
				if (role == null) {
					message = "CPR number and password did not match";
					request.setAttribute("errormessage", message);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				} else if (role.equals("e")) {
					setSession(request, response, cpr, "e");
				} else {
					User user = db.getUser(cpr);
					setSession(request, response, cpr, "c");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void setSession(HttpServletRequest request, HttpServletResponse response, String cpr, String role)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.loggedinuser = cpr;
		this.role = role;
		session.setAttribute("role", role);
		session.setAttribute("loggedinuser", cpr);
		response.sendRedirect("LoginRedirect");
	}

	protected String getLoggedInUser() {
		return loggedinuser;
	}

	protected String getRole() {
		return role;
	}

	public String getMessage() {
		return message;
	}
}
