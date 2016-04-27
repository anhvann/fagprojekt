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
		String accountID, name, value, status, pattern, pattern2;
		DecimalFormatSymbols symbols;
		DecimalFormat decimalFormat, decimalFormat2;
		BigDecimal interest;
		
		try {
			db = new Database();
			User user = new User(db, cpr);
			String[] columns = { "CPRNo", "Email", "Password", "FullName", "Address", "Phone", "DateOfBirth","Postcode", "RoleID" };
			LinkedList<String> userInfo;
			LinkedList<Account> accounts;
			userInfo = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
			user.setInfo(userInfo);
			accounts = db.getAccounts(user);
			user.setAccounts(accounts);
			
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
	 				accountID = request.getParameter("accountID");
					request.setAttribute("cpr", cpr);
					request.setAttribute("accountID", accountID);
					request.setAttribute("transactions", user.getTransactions());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
					break;
				case "newaccount" :
					request.setAttribute("cpr", cpr);
					request.setAttribute("user", user);
					request.getRequestDispatcher("newaccount.jsp").forward(request, response);
					break;
				case "createaccount" :
					name = request.getParameter("name");
					value = request.getParameter("interest");
					status = request.getParameter("status");

					symbols = new DecimalFormatSymbols();
					symbols.setDecimalSeparator('.');
					pattern = "#.###";
					pattern2 = "#.##";
					decimalFormat = new DecimalFormat(pattern, symbols);
					decimalFormat2 = new DecimalFormat(pattern2, symbols);
					decimalFormat.setParseBigDecimal(true);
					decimalFormat2.setParseBigDecimal(true);
					try {
						interest = (BigDecimal) decimalFormat.parse(value);
						accountID = generateAccountID(user);
						BigDecimal balance = (BigDecimal) decimalFormat2.parse("0");
						Account account = new Account(user, accountID, name, balance, interest, status);
						user.addAccount(account);
						
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case "closeaccount" :
					accountID = request.getParameter("accountID");
					user.closeAccount(accountID);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
				case "editaccount" :
					accountID = request.getParameter("accountID");
					boolean boolStatus;
					if (user.getAccount(accountID).getStatus().equals("1")) {
						boolStatus = true;
					} else {
						boolStatus = false;
					}
					request.setAttribute("name", user.getAccount(accountID).getName());
					request.setAttribute("interest", user.getAccount(accountID).getInterest());
					request.setAttribute("status", boolStatus);
					
					request.getRequestDispatcher("editaccount.jsp").forward(request, response);
					break;
				case "changeaccount" : 
					accountID = request.getParameter("accountID");
					name = request.getParameter("name");
					value = request.getParameter("interest");
					status = request.getParameter("status");

					symbols = new DecimalFormatSymbols();
					symbols.setDecimalSeparator('.');
					pattern = "#.###";
					decimalFormat = new DecimalFormat(pattern, symbols);
					decimalFormat.setParseBigDecimal(true);
					try {
						interest = (BigDecimal) decimalFormat.parse(value);
						Account account = user.getAccount(accountID);
						account.setName(name);
						account.setInterest(interest);
						account.setStatus(status);
						user.editAccount(account);
						
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ParseException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
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
