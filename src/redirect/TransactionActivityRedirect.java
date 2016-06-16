package redirect;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import model.Database;
import model.Transaction;

@WebServlet("/TransactionActivityRedirect")
public class TransactionActivityRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionActivityRedirect() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Database db = new Database(request.getSession());
			String accountID = request.getParameter("accountID");
			String message = request.getParameter("message");
			String cpr = request.getParameter("ID");
			request.setAttribute("cpr", cpr);
			switch (message) {
		    	case "Deposit Invalid Account" :
					request.setAttribute("errormessage", "Account does not exist");
					request.getRequestDispatcher("deposit.jsp").forward(request, response);
					break;
		    	case "Withdraw Invalid Account" :
		    		request.setAttribute("errormessage", "Account does not exist");
					request.getRequestDispatcher("withdraw.jsp").forward(request, response);
					break;
		    	case "Withdraw Insufficient Balance" :
		    		request.setAttribute("errormessage", "Insufficient Balance");
		    		request.getRequestDispatcher("withdraw.jsp").forward(request,  response);
		    		break;
		    	case "Transfer Insufficient Balance" : 
		    		request.setAttribute("errormessage", "Insufficient Balance");
		    		request.getRequestDispatcher("transfer.jsp").forward(request,  response);
		    		break;
		    	case "Transfer Invalid Account" :
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.setAttribute("errormessage", "Receiving account does not exist");
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.setAttribute("errormessage", "One of the accounts does not exist");
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	case "Same account failure" :
		    		request.setAttribute("errormessage", "Sending and receiving account is the same");
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	default :
		    		request.setAttribute("message", message);
					request.setAttribute("cpr", cpr);
					request.setAttribute("toast", true);
					request.setAttribute("accountID", accountID);
					ArrayList<Transaction> list = db.getTransactions(accountID);
					request.setAttribute("transactions", list);
					request.setAttribute("balance", list.get(list.size()-1).getBalanceString());
					request.setAttribute("accountName", db.getAccount(accountID).getName());
					request.setAttribute("ISOCode", db.getAccount(accountID).getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
					break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
