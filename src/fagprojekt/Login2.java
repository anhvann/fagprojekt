package fagprojekt;

import Controller.BankApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login3")
public class Login2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BankApp bankApp = null;
    ResultSet rs = null;
	String role, password, email = "";
	boolean validate = false;
	
	public Login2() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//doGet(request, response);
		
        password = request.getParameter("password");
        email = request.getParameter("email");
        
        if(bankApp == null) {
        	// create new instance of BankApp controller
        	bankApp = new BankApp();

			try {
				rs = bankApp.getAccountInfo(email, password);
				checkInfo();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        try { 
        	if (validate) {
				if (role.equals("Employee")) {
					response.sendRedirect("search.jsp");
				} else if (role.equals("Client")){
					response.sendRedirect("activity.jsp");
				}
			} else {
				String message = "E-mail address or password was incorrect";
				response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		        

	}

	protected void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
		RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher(url);
		System.out.println(RequetsDispatcherObj != null);
		System.out.println(request != null);
		System.out.println(response != null);
		RequetsDispatcherObj.forward(request, response);
	}
	
	public boolean checkInfo() {
		boolean validate = false;
		
		try {
			while (!validate && rs.next()) {
				if (rs.getString("Email").equals(email) && rs.getString("Password").equals(password)) {
					validate = true;
					role = rs.getString("Role");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return validate;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
}
