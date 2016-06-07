package controller;

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
				cpr = db.getOwner(accountID);
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
				accountID = generateAccountID(user);
				account = new Account(user, accountID, accountName, balance, interest, ISOCode, new LinkedList<Transaction>());
				message = user.addAccount(account);
				request.setAttribute("message", message);
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			case "closeaccount":
				message = db.closeAccount(accountID);
				if (message.equals("Cannot delete because account has money")) {
					request.setAttribute("cpr", cpr);
					request.setAttribute("message", message);
					request.setAttribute("accountID", accountID);
					request.setAttribute("accountName", accountName);
					request.setAttribute("transactions", db.getTransactions(accountID));
					request.setAttribute("balance", formatNumber(user.getBalance(accountID)));
					request.setAttribute("ISOCode", db.getAccount(accountID).getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
				} else {
					user.closeAccount(accountID);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("name", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
				}
				break;
			case "editaccount":
				request.setAttribute("accountID", accountID);
				request.setAttribute("cpr", cpr);
				request.setAttribute("name", user.getAccount(accountID).getName());
				request.setAttribute("interest", user.getAccount(accountID).getInterest());
				request.setAttribute("ISOCode", user.getAccount(accountID).getISOCode());
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
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private String formatNumber(BigDecimal value) {
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("###,##0.00", symbols);
		return formatter.format(value.longValue());
	}

	private BigDecimal getBigDecimal(String string) {
		BigDecimal value = new BigDecimal(string.replaceAll(",", ""));
		return value;
	}

	private String generateAccountID(User user) {
		int min = 0;
		int max = 9;
		String ID = "";
		Boolean generated = false;
		ID += 0 + (int) (Math.random() * max);
		while (!generated) {
			for (int i = 0; i < 13; i++) {
				ID += min + (int) (Math.random() * max);
			}
			generated = true;
			for (Account existingAccount : db.getAllAccounts()) {
				if (existingAccount.getAccountID().equals(ID)) {
					generated = false;
					break;
				}
			}
		}
		return ID;
	}

	public String getMessage(){
		return message;
	}
	
	//For test
	public String getAccountID(){
		return accountID;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doGet(request, response);
	}

}
