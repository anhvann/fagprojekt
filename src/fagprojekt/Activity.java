package fagprojekt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.BankApp;

/**
 * Servlet implementation class Activity
 */
@WebServlet("/Activity")
public class Activity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private BankApp bankApp = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Activity() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (bankApp == null) {
			bankApp = new BankApp();
		}
		//TODO
		//Get name from chosen row in search.jsp and set attribute
		//Call getAccounts from bankApp at set attributes
		int userID = 0;
		LinkedList<String> accounts;
		try {
			accounts = bankApp.getAccounts(userID);
		} catch (ClassNotFoundException | SQLException e) {
			accounts = null;
			e.printStackTrace();
		}
		
		String name = "";
		request.setAttribute(name, "name");
		for (String account : accounts) {
			request.setAttribute(account, "account");
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
