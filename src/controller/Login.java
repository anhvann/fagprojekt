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

	public Login() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		String cpr = request.getParameter("cpr");
		String password = request.getParameter("password");
		try {
			db = new Database(request.getSession());
			String role = "";
			String[] columns = { "Password", "RoleID" };
			LinkedList<String> results = db
					.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
			if (!results.isEmpty()) {
				String dbpassword = results.get(0);
				if (dbpassword.equals(password)) {
					role = results.get(1);
				}
			}

			if (role.equals("e")) {
				setSession(request, response, cpr, "e", null);
			} else if (role.equals("c")) {
				User user = db.getUser(cpr);
				setSession(request, response, cpr, "c", user);
			} else {
				String message = "CPR Number and password did not match";
				request.setAttribute("message", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void setSession(HttpServletRequest request, HttpServletResponse response, String cpr, String role, User user) throws ServletException, IOException {
		HttpSession session = request.getSession();
		this.loggedinuser = cpr;
		this.role = role;
		session.setAttribute("role", role);
		session.setAttribute("loggedinuser", cpr);
		if (role.equals("e")) {
			request.getRequestDispatcher("search.jsp").forward(request, response);
		} else {
			request.setAttribute("accounts", user.getAccounts());
			request.setAttribute("name", user.getName());
			request.setAttribute("cpr", cpr);
			request.getRequestDispatcher("accounts.jsp").forward(request, response);
		}
	}

	protected String getLoggedInUser() {
		return loggedinuser;
	}

	protected String getRole() {
		return role;
	}
}
