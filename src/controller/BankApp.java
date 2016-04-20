package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.*;

@WebServlet("/BankApp")
public class BankApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String user;
	Database db;
	Login login;
       
    public BankApp() throws ClassNotFoundException, SQLException {
        super();
        db = new Database();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	public void setLoggedInUser(String user) {
		this.user = user;
	}

	public Database getDatabase() {
		return db;
	}

}
