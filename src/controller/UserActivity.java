package controller;

import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Database;
import model.Transaction;
import model.User;

/**
 * Servlet implementation class UserActivity
 */
@WebServlet("/UserActivity")
public class UserActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Database db = null;
	private String message;

	public UserActivity() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String cpr = request.getParameter("ID");

		try {
			db = new Database(request.getSession());
			User user;
			switch (action) {
			case "viewuser":
				user = db.getUser(cpr);
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			case "edit":
				user = db.getUser(cpr);
				request.setAttribute("cpr", cpr);
				request.setAttribute("email", user.getEmail());
				request.setAttribute("password", user.getPassword());
				request.setAttribute("phone", user.getPhone());
				request.setAttribute("name", user.getName());
				request.setAttribute("address", user.getAddress());
				request.setAttribute("postcode", user.getPostCode());
				request.setAttribute("city", db.getCity(user.getPostCode()));
				request.setAttribute("date", user.getDateOfBirth());
				request.getRequestDispatcher("userInfo.jsp").forward(request, response);
				break;
			case "delete":
				message = db.deleteUser(cpr);
				if (message.equals("User deleted")) {
					response.sendRedirect("UserActivityRedirect?action=" + action + "&cpr=" + cpr + "&message=" + message);
				} else {
					response.sendRedirect("UserActivityRedirect?action=" + action + "&cpr=" + cpr + "&message=" + message);
				}
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String postcode = request.getParameter("postcode");
		String date = request.getParameter("date");
		String phone = request.getParameter("phone");

		try {
			db = new Database(request.getSession());
			User user;

			switch (action) {
			case "register":
				Date dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				java.sql.Date dateSQL = new java.sql.Date(dateObject.getTime());
				message = db.register(cpr, email, password, name, phone, address, dateSQL, postcode);
				user = db.getUser(cpr);
				if (message.equals("User registered successfully")) {
					response.sendRedirect("UserActivityRedirect?action=" + action + "&cpr=" + cpr + "&message=" + message);
				} else {
					response.sendRedirect("UserActivityRedirect?action=" + action + "&cpr=" + cpr + "&email=" + email
							+ "&name=" + name + "&phone=" + phone + "&address=" + address + "&date=" + date
							+ "&postcode=" + postcode + "&message=" + message);
				}
				break;
			case "change":
				user = db.getUser(cpr);
				dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				dateSQL = new java.sql.Date(dateObject.getTime());
				message = db.editUser(cpr, email, password, name, address, postcode, dateSQL, phone);
				response.sendRedirect("UserActivityRedirect?action=" + action + "&cpr=" + cpr + "&message=" + message);
				break;
			}
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	public String getMessage() {
		return message;
	}
}
