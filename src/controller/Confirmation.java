package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Database;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Database db = new Database();
			String accountID = (String) request.getAttribute("accountID");
			String accountName = (String) request.getAttribute("accountName");
			String message = (String) request.getAttribute("message");
			String cpr = db.getOwner(accountID);
			switch (message) {
		    	case "Deposit failure" :
					request.setAttribute("message", "Account does not exist");
					request.getRequestDispatcher("deposit.jsp").forward(request, response);
					break;
		    	case "Withdraw failure" :
		    		request.setAttribute("message", "Account does not exist");
					request.getRequestDispatcher("withdraw.jsp").forward(request, response);
					break;
		    	case "Money Transaction failure" :
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.setAttribute("message", "Receiving account does not exist");
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.setAttribute("message", "One of the accounts does not exist");
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	case "Same account failure" :
		    		request.setAttribute("message", "Sending and receiving account is the same");
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	default :
		    		request.setAttribute("message", "");
					request.setAttribute("cpr", cpr);
					request.setAttribute("accountID", accountID);
					request.setAttribute("transactions", db.getTransactions(accountID));
					request.setAttribute("balance", db.getTransactions(accountID).getLast().getBalanceString());
					request.setAttribute("accountName", db.getAccount(accountID).getName());
					request.setAttribute("ISOCode", db.getAccount(accountID).getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
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
