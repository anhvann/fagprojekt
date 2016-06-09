package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Database;
import model.User;

@WebServlet("/LoginRedirect")
public class LoginRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginRedirect() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = (String) request.getSession().getAttribute("role");
		String cpr = (String) request.getSession().getAttribute("loggedinuser");
		Database db;
		try {
			db = new Database(request.getSession());
			User user = db.getUser(cpr);
			if (role.equals("e")) {
				request.setAttribute("toast", false);
				request.getRequestDispatcher("search.jsp").forward(request, response);
			} else {
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
