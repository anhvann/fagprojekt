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
@WebServlet("/AccountActivity")
public class AccountActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Database db = null;
	
	public AccountActivity() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String accountName = request.getParameter("accountName");
		String name = request.getParameter("name");
		String value = request.getParameter("interest");
		String ISOCode = request.getParameter("ISOCode");
		
		try {
			db = new Database();
			if (cpr == null) {
				cpr = db.getOwner(accountID);
			}
			System.out.println(cpr);
			User user = db.getUser(cpr);
			String message;
			
			switch (action) {
				case "viewaccount" :
					request.setAttribute("cpr", cpr);
					request.setAttribute("accountID", accountID);
					request.setAttribute("accountName", accountName);
					request.setAttribute("transactions", db.getTransactions(accountID));
					request.setAttribute("balance", formatNumber(user.getBalance(accountID)));
					request.setAttribute("ISOCode", user.getAccount(accountID).getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
					break;
				case "newaccount" :
					request.setAttribute("cpr", cpr);
					request.setAttribute("user", user);
					request.getRequestDispatcher("newaccount.jsp").forward(request, response);
					break;
				case "createaccount" :
					try {
						BigDecimal interest = getBigDecimal(value);
						BigDecimal balance = getBigDecimal("0");
						accountID = generateAccountID(user);
						Account account = new Account(user, accountID, name, balance, interest, ISOCode);
						message = user.addAccount(account);
						System.out.println(message);
						request.setAttribute("message", message);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case "closeaccount" :
					message = db.closeAccount(accountID);
					System.out.println(message);
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
						System.out.println("user: " + user);
						user.closeAccount(accountID);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					}
					break;
				case "editaccount" :
					request.setAttribute("accountID", accountID);
					request.setAttribute("cpr", cpr);
					request.setAttribute("name", user.getAccount(accountID).getName());
					request.setAttribute("interest", user.getAccount(accountID).getInterest());
					request.setAttribute("ISOCode", user.getAccount(accountID).getISOCode());
					System.out.println("edit");
					request.getRequestDispatcher("editaccount.jsp").forward(request, response);
					break;
				case "changeaccount" :
					try {
						BigDecimal interest = getBigDecimal(value);
						Account account = user.getAccount(accountID);
						account.setName(name);
						account.setInterest(interest);
						account.setISOCode(ISOCode);
						message = user.editAccount(account);
						System.out.println(message);
						request.setAttribute("message", message);
						request.setAttribute("accounts", user.getAccounts());
						request.setAttribute("fullname", user.getName());
						request.setAttribute("cpr", cpr);
						request.getRequestDispatcher("accounts.jsp").forward(request, response);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
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
