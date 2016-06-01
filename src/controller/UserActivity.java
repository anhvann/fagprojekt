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
	
    public UserActivity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String zipcode = request.getParameter("zipcode");
		String date = request.getParameter("date");
		String phone = request.getParameter("phone");
		
		try {
			db = new Database();
			User user;
			String message;
			
			switch (action) {
				case "register" :
					try {
						Date dateObject = new SimpleDateFormat("dd-MM-yyyy").parse(date);
						java.sql.Date dateSQL = new java.sql.Date(dateObject.getTime());
						db.register(cpr, email, password, name, address, zipcode, dateSQL, phone);
						user = db.getUser(cpr);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException e1) {
						e1.printStackTrace();
					} 
					break;
				case "viewuser" :
					user = db.getUser(cpr);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
				case "edit" :
					user = db.getUser(cpr);
					request.setAttribute("cpr", cpr);
					request.setAttribute("email", user.getEmail());
					request.setAttribute("password", user.getPassword());
					request.setAttribute("phone", user.getPhone());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("address", user.getAddress());
					request.setAttribute("postcode", user.getPostCode());
					request.setAttribute("city", db.getCity(user.getPostCode()));
					request.setAttribute("date", user.getDateOfBirth());
					request.getRequestDispatcher("userInfo.jsp").forward(request, response);
					break;
				case "change" :
					try {
						Date dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
						java.sql.Date dateSQL = new java.sql.Date(dateObject.getTime());
						user = db.getUser(cpr);
						message = db.editUser(cpr, email, password, name, address, zipcode, dateSQL, phone);
						request.setAttribute("message", message);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
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