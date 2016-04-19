package fagprojekt;

import Controller.BankApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
<<<<<<< HEAD
//@WebServlet("/Login")
//public class Login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private static Connection db2Conn;
//	private static Statement stmt;
//	BankApp bankApp = null;
//
//	public Login() {
//		super();
//	}
//
//	/**
//	 * @see Servlet#init(ServletConfig)
//	 */
//	public void init(ServletConfig config) throws ServletException {
//		String DB_USER = "DTU11";
//		String DB_PASSWORD = "FAGP2016";
//		try {
//			Class.forName("com.ibm.db2.jcc.DB2Driver");
//			db2Conn = DriverManager.getConnection("jdbc:db2://192.86.32.54:5040/DALLASB:" + "user=" + DB_USER + ";"
//					+ "password=" + DB_PASSWORD + ";");
//		} catch (SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//        // This is for Sreen Testing///////////////
//        PrintWriter out = response.getWriter();////
//        ///////////////////////////////////////////
//         
//        String password = request.getParameter("password");
//         
//        if(bankApp == null) {
//            // create new instance of BankApp controller
//            bankApp = new BankApp();
//                     
//            ArrayList<String> list = new ArrayList<String>();
//             
//            try {
//                list = bankApp.getInfo(password);
//                for (String s : list) {
//                    out.println(s);
//                }
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
////		String email = request.getParameter("email");
////		String password = request.getParameter("password");
//
////		if (bankApp == null) {
////			// create new instance of BankApp controller
////			bankApp = new BankApp();
////
////			try {
////				String role = bankApp.getRole(email, password);
////				if (role.equals("employee")) {
////					response.sendRedirect("search.jsp");
////				} else if (role.equals("client")) {
////					response.sendRedirect("activity.jsp");
////				} else {
////					String message = "E-mail address or password was incorrect";
////					response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
////				}
////			} catch (SQLException e) {
////				e.printStackTrace();
////			}
////		}
//
//	}
//
//	protected void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
//			throws ServletException, IOException {
//		RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher(url);
//		System.out.println(RequetsDispatcherObj != null);
//		System.out.println(request != null);
//		System.out.println(response != null);
//		RequetsDispatcherObj.forward(request, response);
//	}
//}
// package fagprojekt;
//
// import java.io.IOException;
// import java.net.URLEncoder;
// import java.sql.*;
// import java.util.Properties;
//
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
/// **
// * Servlet implementation class Login
// */
 @WebServlet("/Login")
 public class Login extends HttpServlet {
	 private static final long serialVersionUID = 1L;

	 public Login() {
		 super();
	 }

	 protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	 
	 }

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
		 System.out.println("yeh");
		 String email = request.getParameter("email");
		 String password = request.getParameter("password");
		 String role = "";

		 try {
			 Class.forName("com.ibm.db2.jcc.DB2Driver");
		
		 } catch (ClassNotFoundException e) {
			 System.out.println("Driver not found");
			 e.printStackTrace();
		 }

		 Properties properties = new Properties();
		 properties.put("user", "DTU12");
		 properties.put("password", "FAGP2016");
		 String url =
		 "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
		 Connection connection = null;
		 PreparedStatement statement = null;
		 ResultSet rs = null;
		 Boolean validate = false;
		 try {
			 connection = DriverManager.getConnection(url, properties);
			 statement = connection.prepareStatement("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"Email\"=?");
			 statement.setString(1, email);
			 rs = statement.executeQuery();
			 while (!validate && rs.next()) {
				 if (rs.getString("Email").equals(email) && rs.getString("Password").equals(password)) {
					 validate = true;
					 role = rs.getString("Role");
				 }
			 }
			 rs.close();
			 statement.close();

			 } catch (SQLException e) {
			 e.printStackTrace();

			 }
		 if (validate) {
			 if (role.equals("Employee")) {
				 response.sendRedirect("search.jsp");
			 } else {
			 response.sendRedirect("activity.jsp");
			 }
 		 } else {
			 String message = "E-mail address or password was incorrect";
			 response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message,
			 "UTF-8"));
		 }
	 }

 }
=======
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Connection db2Conn;
	private static Statement stmt;
	BankApp bankApp = null;

	public Login() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (email.isEmpty() || password.isEmpty()) {
			String message = "Please fill in all fields";
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		if (bankApp == null) {
			bankApp = new BankApp();
		}
		try {
			String role = bankApp.getRole(email, password);
			if (role.equals("e")) {
				response.sendRedirect("search.jsp");
			} else if (role.equals("c")) {
				response.sendRedirect("activity.jsp");
			} else {
				String message = "E-mail address or password was incorrect";
				request.setAttribute("message", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {
		RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher(url);
		System.out.println(RequetsDispatcherObj != null);
		System.out.println(request != null);
		System.out.println(response != null);
		RequetsDispatcherObj.forward(request, response);
	}
}
>>>>>>> branch 'master' of https://github.com/anhvann/fagprojekt.git
