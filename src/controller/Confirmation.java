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
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			Database db = new Database();
			String accountID = request.getParameter("accountID");
			String accountName = request.getParameter("accountName");
			String cpr = db.getOwner(accountID);
			request.setAttribute("cpr", cpr);
			request.setAttribute("accountID", accountID);
			request.setAttribute("transactions", db.getTransactions(accountID));
			request.setAttribute("balance", db.getTransactions(accountID).getLast().getBalanceString());
			request.setAttribute("accountName", db.getAccount(accountID).getName());
			request.setAttribute("ISOCode", db.getAccount(accountID).getISOCode());
			request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
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
