package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.sql.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Database;
import model.Transaction;
import model.User;

@WebServlet("/Transactions")
public class Transactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Transactions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String accountID2 = request.getParameter("accountID2");
		String transactionName = request.getParameter("transName");
		String amountString = request.getParameter("amount");
		String ISOCode = request.getParameter("ISOCode");
		String cpr = request.getParameter("ID");
		if (accountID.isEmpty() || amountString.isEmpty() || (action.equals("transfer") && (accountID2.isEmpty() || transactionName.isEmpty()))) {
			String message = "Please fill in all fields";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
		}
		BigDecimal amount = null;
		try {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#.##";			
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			amount = (BigDecimal) decimalFormat.parse(amountString);
	    } catch (NumberFormatException | ParseException ignore) {
	    	String message = "Please type in a valid amount";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
	    }
		
		try {
			Database db = new Database();
			String message;
			if (cpr == null) {
				cpr = db.getOwner(accountID);
			}
			
		    switch (action) {
		    	case "deposit" :
					message = db.processTransaction("Deposit", accountID, accountID2, amount, ISOCode, transactionName);
					System.out.println(message);
					redirect(request, response, accountID, cpr);
					break;
		    	case "withdraw" :
		    		message = db.processTransaction("Withdraw", accountID, accountID2, amount, ISOCode, transactionName);
					System.out.println(message);
		    		if (message.equals("Withdrawing under 0 is not allowed")) {
		    			request.setAttribute("message", message);
		    			request.getRequestDispatcher("withdraw.jsp").forward(request, response);
		    		} else {
		    			redirect(request, response, accountID, cpr);
		    		}
		    		break;
		    	case "transfer" :
		    		message = db.processTransaction("Transfer", accountID, accountID2, amount, ISOCode, transactionName);
					System.out.println(message);
					if (message.equals("")) {
		    			request.setAttribute("message", message);
		    			request.setAttribute("cpr", cpr);
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		} else {
		    			redirect(request, response, accountID, cpr);
		    		}
		    		break;
		    }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	private void redirect(HttpServletRequest request, HttpServletResponse response, String accountID, String cpr)throws ServletException, IOException {
		response.sendRedirect("Confirmation?ID="+cpr+"&accountID="+accountID);
	}
}
