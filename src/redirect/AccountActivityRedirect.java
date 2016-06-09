package redirect;

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


@WebServlet("/AccountActivityRedirect")
public class AccountActivityRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String message;

	public AccountActivityRedirect() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String newCPR = request.getParameter("newCPR");
		
		try {
			Database db = new Database(request.getSession());
			User user = db.getUser(cpr);
			Account account = db.getAccount(accountID);
			switch (action) {
			case "createaccount":
				request.setAttribute("message", message);
				request.setAttribute("accounts", user.getAccounts());
				request.setAttribute("name", user.getName());
				request.setAttribute("cpr", cpr);
				request.setAttribute("toast", true);
				request.getRequestDispatcher("accounts.jsp").forward(request, response);
				break;
			case "closeaccount":
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
			case "changeaccount":
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
			case "share" :
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
