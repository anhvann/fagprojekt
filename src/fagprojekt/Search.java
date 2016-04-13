package fagprojekt;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.BankApp;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	ResultSet rs;
	Statement statement;
	
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BankApp app = new BankApp();
		Login2 login = new Login2();
//		try {
//			rs = app.getDatabase();
//		} catch (SQLException e1) {
//			System.out.println("ResultSet Empty");
//			e1.printStackTrace();
//		}
		String search = request.getParameter("Search");
		System.out.println("search = " + search);
		
		ResultSetMetaData rsmd = null;
		int numCols;
		ArrayList<String> colNames = new ArrayList<String>();
		System.out.println("dafuq");
		try {
			System.out.println("SELECT * FROM DTUGRP05.USERS WHERE \"UserName\" LIKE '%" + search + "%' OR \"Address\" LIKE '%" + search + "%' OR \"Email\" LIKE '%" + search + "%' OR \"Phone\" LIKE '%" + search + "%'");
			rs = statement.executeQuery("SELECT * FROM DTUGRP05.USERINFO");
    		System.out.println("hej");
			rsmd = rs.getMetaData();
    		numCols = rsmd.getColumnCount();
    		for (int i = 1; i <= numCols; i++) {
    			colNames.add(rsmd.getColumnName(i));
    		}
    		PrintWriter out = response.getWriter();
    		out.println("<table><thead><tr>");
    		for (String name : colNames) {
    			out.println("<td>"+name+"</td>");
    		}
    		out.println("</tr></thead>");
    		out.println("<tbody>");
    		while(rs.next()) {
	    		out.println("<tr>");
	    		for (int i = 1; i <= numCols; i++) {
	    		out.println("<td>"+rs.getString(i)+"</td>");
	    		}
	    		out.println("</tr>");
    		}
    		out.println("</tbody></table>");
		} catch (SQLException e) {
			System.out.println("fuck lortet");
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
