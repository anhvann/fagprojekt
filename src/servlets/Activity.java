package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Database;
import model.Account;
import model.Transaction;
import model.User;

/**
 * Servlet implementation class Activity
 */
@WebServlet("/Activity")
public class Activity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Database db = null;
	
	public Activity() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String name = request.getParameter("name");
		String value = request.getParameter("interest");
		String status = request.getParameter("status");
		String pattern = "#.###";
		DecimalFormatSymbols symbols;
		DecimalFormat decimalFormat;
		BigDecimal interest;
		
		try {
			db = new Database();
			User user = new User(db, cpr);
			String[] columns = { "CPRNo", "Email", "Password", "FullName", "Address", "Phone", "DateOfBirth","Postcode", "RoleID" };
			LinkedList<String> userInfo;
			LinkedList<Account> accounts;
			LinkedList<Transaction> transactions;
			userInfo = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
			user.setInfo(userInfo);
			accounts = db.getAccounts(user);
			user.setAccounts(accounts);
			String message;
			
			switch (action) {
				case "viewuser" :
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
				case "edit" :
					request.setAttribute("email", user.getEmail());
					request.setAttribute("password", user.getPassword());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("address", user.getAddress());
					request.setAttribute("postcode", user.getPostCode());
					request.getRequestDispatcher("userInfo.jsp").forward(request, response);
					break;
				case "viewaccount" :
					request.setAttribute("cpr", cpr);
					request.setAttribute("accountID", accountID);
					request.setAttribute("transactions", db.getTransactions(accountID));
					System.out.println(user.getBalance(accountID));
					request.setAttribute("balance", user.getBalance(accountID));
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
					break;
				case "newaccount" :
					request.setAttribute("cpr", cpr);
					request.setAttribute("user", user);
					request.getRequestDispatcher("newaccount.jsp").forward(request, response);
					break;
				case "createaccount" :
					symbols = new DecimalFormatSymbols();
					symbols.setDecimalSeparator('.');
					decimalFormat = new DecimalFormat(pattern, symbols);
					decimalFormat.setParseBigDecimal(true);
					try {
						interest = (BigDecimal) decimalFormat.parse(value);
						accountID = generateAccountID(user);
						BigDecimal balance = new BigDecimal(String.valueOf(0));
						Account account = new Account(user, accountID, name, balance, interest, status);
						message = user.addAccount(account);
						System.out.println(message);
						request.setAttribute("message", message);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case "closeaccount" :
					message = user.closeAccount(accountID);
					System.out.println(message);
					request.setAttribute("message", message);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
				case "editaccount" :
					request.setAttribute("accountID", accountID);
					request.setAttribute("cpr", cpr);
					request.setAttribute("name", user.getAccount(accountID).getName());
					request.setAttribute("interest", user.getAccount(accountID).getInterest());
					request.setAttribute("status", user.getAccount(accountID).getStatus());
					System.out.println("edit");
					request.getRequestDispatcher("editaccount.jsp").forward(request, response);
					break;
				case "changeaccount" :
					symbols = new DecimalFormatSymbols();
					symbols.setDecimalSeparator('.');
					decimalFormat = new DecimalFormat(pattern, symbols);
					decimalFormat.setParseBigDecimal(true);
					try {
						interest = (BigDecimal) decimalFormat.parse(value);
						Account account = user.getAccount(accountID);
						account.setName(name);
						account.setInterest(interest);
						account.setStatus(status);
						message = user.editAccount(account);
						System.out.println(message);
						request.setAttribute("message", message);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case "deposit" : 
					request.setAttribute("account", accountID);
					request.getRequestDispatcher("deposit.jsp").forward(request, response);
					break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}


		}

	private String generateAccountID(User user) {
		int min = 0;
		int max = 9;
		String ID = "";
		Boolean generated = false;
		ID += 0 + (int) (Math.random()*max);
		while (!generated) {
			for (int i = 0; i < 13; i++) {
				ID += min + (int) (Math.random() * max);
			}
			generated = true;
			for (Account existingAccount : user.getAccounts()) {
				if (existingAccount.getAccountID().equals(ID)) {
					generated = false;
					break;
				}
			}
		}
		return ID;
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
