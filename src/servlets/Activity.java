package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.BankApp;
import model.Account;
import model.User;

/**
 * Servlet implementation class Activity
 */
@WebServlet("/Activity")
public class Activity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BankApp bankApp = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Activity() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		User user = new User(cpr);
		
		bankApp = new BankApp();

		String[] columns = {"CPRNo", "Email", "Password", "FullName", "Address", "Phone", "DateOfBirth", "Postcode", "RoleID"};
		LinkedList<String> userInfo;
		LinkedList<Account> accounts;
		
		try {
			userInfo = bankApp.queryExecution("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
			user.setInfo(userInfo);
			accounts = bankApp.getAccounts(cpr);
			user.setAccounts(accounts);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		if (action.equals("view")) {	
			request.setAttribute("accounts", user.getAccounts());
			request.setAttribute("fullname", user.getName());
			request.setAttribute("cpr", cpr);
			request.getRequestDispatcher("activity.jsp").forward(request, response);
		} else if (action.equals("edit")){
			System.out.println(user.getEmail());
			request.setAttribute("email", user.getEmail());
			System.out.println(user.getEmail());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("fullname", user.getName());
			request.setAttribute("address", user.getAddress());
			request.setAttribute("postcode", user.getPostCode());
			request.getRequestDispatcher("userInfo.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
