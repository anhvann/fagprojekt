package servlets;

import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
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
 * Servlet implementation class UserActivity
 */
@WebServlet("/UserActivity")
public class UserActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Database db = null;
	
    public UserActivity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String zipcode = request.getParameter("zipcode");
		String date = request.getParameter("date");
		String phone = request.getParameter("phone");
		
		try {
			db = new Database();
			User user = db.getUser(cpr);
			String message;
			
			switch (action) {
			// updated - user.getAccounts()?
				case "register" : 
					db.register(cpr, email, password, name, address, zipcode, date, phone);
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("ID", cpr);
					request.setAttribute("fullname", name);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
				case "viewuser" :
					request.setAttribute("accounts", user.getAccounts());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("cpr", cpr);
					request.getRequestDispatcher("accounts.jsp").forward(request, response);
					break;
			// not updated
				case "edit" :
					request.setAttribute("email", user.getEmail());
					request.setAttribute("password", user.getPassword());
					request.setAttribute("fullname", user.getName());
					request.setAttribute("address", user.getAddress());
					request.setAttribute("postcode", user.getPostCode());
					request.getRequestDispatcher("userInfo.jsp").forward(request, response);
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
