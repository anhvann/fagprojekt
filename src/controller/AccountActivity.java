package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
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
 * Servlet implementation class Activity
 */
@WebServlet("/AccountActivity")
public class AccountActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database db = null;
	private String message;
	private String accountID;

	public AccountActivity() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		accountID = request.getParameter("accountID");
		String accountName = request.getParameter("accountName");
		String value = request.getParameter("interest");
		String ISOCode = request.getParameter("ISOCode");
		Account account;

		try {
			db = new Database(request.getSession());
			if (cpr == null) {
				System.out.println("cpr is null");
			}
			User user = db.getUser(cpr);

			switch (action) {
			case "viewaccount":
				account = db.getAccount(accountID);
				request.setAttribute("cpr", cpr);
				request.setAttribute("accountID", accountID);
				request.setAttribute("accountName", account.getName());
				request.setAttribute("transactions", account.getTransactions());
				request.setAttribute("balance", account.getBalanceString());
				request.setAttribute("ISOCode", account.getISOCode());
				request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
				break;
			case "newaccount":
				request.setAttribute("cpr", cpr);
				request.setAttribute("user", user);
				request.getRequestDispatcher("newaccount.jsp").forward(request, response);
				break;
			case "createaccount":
				BigDecimal interest = getBigDecimal(value);
				BigDecimal balance = getBigDecimal("0");
				accountID = db.generateAccountID();
				LinkedList<User> owners = new LinkedList<>();
				owners.add(user);
				account = new Account(owners, accountID, accountName, balance, interest, ISOCode, new LinkedList<Transaction>());
				message = user.addAccount(account);
				request.setAttribute("message", message);
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.setAttribute("toast", true);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			case "closeaccount":
				message = db.closeAccount(accountID);
				if (message.equals("Cannot delete because account has money")) {
					account = db.getAccount(accountID);
					request.setAttribute("cpr", cpr);
					request.setAttribute("errormessage", message);
					request.setAttribute("accountID", accountID);
					request.setAttribute("accountName", account.getName());
					request.setAttribute("transactions", account.getTransactions());
					request.setAttribute("balance", account.getBalanceString());
					request.setAttribute("ISOCode", account.getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
				} else if (message.equals("Account deleted")){
					user.closeAccount(accountID);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("name", user.getName());
					request.setAttribute("cpr", cpr);
					request.setAttribute("message", message);
					request.setAttribute("toast", true);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
				}
				break;
			case "editaccount":
				account = db.getAccount(accountID);
				request.setAttribute("accountID", accountID);
				request.setAttribute("cpr", cpr);
				request.setAttribute("name", account.getName());
				request.setAttribute("interest", account.getInterest());
				request.setAttribute("ISOCode", account.getISOCode());
				request.setAttribute("owners", account.getOwners());
				request.getRequestDispatcher("editaccount.jsp").forward(request, response);
				break;
			case "changeaccount":
				interest = getBigDecimal(value);
				account = user.getAccount(accountID);
				account.setName(accountName);
				account.setInterest(interest);
				account.setISOCode(ISOCode);
				message = user.editAccount(account);
				request.setAttribute("message", message);
				request.setAttribute("toast", true);
				request.setAttribute("cpr", cpr);
				request.setAttribute("accountID", accountID);
				request.setAttribute("accountName", account.getName());
				request.setAttribute("transactions", account.getTransactions());
				request.setAttribute("balance", account.getBalanceString());
				request.setAttribute("ISOCode", account.getISOCode());
				request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
				break;
			case "addowner" : 
				request.setAttribute("cpr", cpr);
				request.setAttribute("accountID", accountID);
				request.getRequestDispatcher("addowner.jsp").forward(request, response);
				break;
			case "share" :
				account = db.getAccount(accountID);
				String newCPR = request.getParameter("newOwner");
				message = db.addOwner(accountID, newCPR);
				if (message.equals("Ownership added")) {
					request.setAttribute("message", message);
					request.setAttribute("toast", true);
					request.setAttribute("cpr", cpr);
					request.setAttribute("accountID", accountID);
					request.setAttribute("accountName", account.getName());
					request.setAttribute("transactions", account.getTransactions());
					request.setAttribute("balance", account.getBalanceString());
					request.setAttribute("ISOCode", account.getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
				} else {
					request.setAttribute("errormessage", message);
					request.setAttribute("accountID", accountID);
					request.setAttribute("newCPR", newCPR);
					request.getRequestDispatcher("addowner.jsp").forward(request, response);
				}
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private BigDecimal getBigDecimal(String string) {
		BigDecimal value = new BigDecimal(string.replaceAll(",", ""));
		return value;
	}

	public String getMessage() {
		return message;
	}

	// For test
	public String getAccountID() {
		return accountID;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
