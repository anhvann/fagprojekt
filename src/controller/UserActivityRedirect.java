package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Database;
import model.User;

@WebServlet("/UserActivityRedirect")
public class UserActivityRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database db;
	public UserActivityRedirect() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		
		String cpr = request.getParameter("cpr");
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String postcode = request.getParameter("postcode");
		String date = request.getParameter("date");
		String phone = request.getParameter("phone");

		try {
			db = new Database(request.getSession());
			User user = db.getUser(cpr);

			switch (action) {
			case "register":
				if (message.equals("User registered successfully")) {
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("name", user.getName());
					request.setAttribute("cpr", cpr);
					request.setAttribute("message", message);
					request.setAttribute("toast", true);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
				} else {
					request.setAttribute("ID", cpr);
					request.setAttribute("email", email);
					request.setAttribute("name", name);
					request.setAttribute("phone", phone);
					request.setAttribute("address", address);
					request.setAttribute("date", date);
					request.setAttribute("postcode", postcode);
					request.setAttribute("errormessage", message);
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}
				break;
			case "change":
				request.setAttribute("message", message);
				request.setAttribute("toast", true);
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			case "delete":
				if (message.equals("User deleted")) {
					request.setAttribute("message", message);
					request.setAttribute("toast", true);
					request.getRequestDispatcher("search.jsp").forward(request, response);
				}else {
					user = db.getUser(cpr);
					request.setAttribute("errormessage", message);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("name", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
				}
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
