package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Database;
import model.Transaction;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/General")
public class General extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public General() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String accountID2 = request.getParameter("accountID2");
		String amountString = request.getParameter("amount");
		if (accountID.isEmpty() || amountString.isEmpty() || action.equals("transfer") && accountID2.isEmpty()) {
			String message = "Please fill in all fields";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
		}
		Double amount = null;
		try {
			amount = Double.parseDouble(amountString);
	    } catch (NumberFormatException ignore) {
	    	String message = "Please type in a valid amount";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
	    }
		
		try {
			Database db = new Database();
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
		    if(action.equals("deposit")){
		    	
		    } else if (action.equals("withdraw")){
		    	
		    } else if (action.equals("transfer")){
		    	Transaction t = new Transaction(db.getTransID(), "Deposit", accountID, accountID2, amount , sqlDate);
				db.processTransaction(t);
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
