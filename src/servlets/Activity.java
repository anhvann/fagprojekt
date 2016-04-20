package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.BankApp;
import model.User;

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
		String userID = null;
		if (!request.getParameter("ID").equals(null)) {
			userID = request.getParameter("ID");
		}
		System.out.println(userID);
		User user = new User(userID);
		
		if (bankApp == null) {
			bankApp = new BankApp();
		}

		LinkedList<String> info;
		try {
			info = bankApp.getInfo(userID);
		} catch (ClassNotFoundException | SQLException e) {
			info = null;
			e.printStackTrace();
		}
		user.setInfo(info); 
		
		request.setAttribute("email", user.getEmail());
		request.setAttribute("password", user.getPassword());
		request.setAttribute("fullname", user.getName());
		request.setAttribute("address", user.getAddress());
		request.setAttribute("postcode", user.getPostCode());
		request.setAttribute("city", user.getCity());
		
		request.getRequestDispatcher("userInfo.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
