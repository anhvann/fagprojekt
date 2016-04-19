package fagprojekt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
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
	BankApp bankApp = null;
	
	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (bankApp == null) {
			bankApp = new BankApp();
		}
		String input = request.getParameter("searchfield");
		LinkedList<String> results = null;
		
		try {
			results = bankApp.searchFor(input);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
		// try {
		// rs = app.getDatabase();
		// } catch (SQLException e1) {
		// System.out.println("ResultSet Empty");
		// e1.printStackTrace();
		// }
		// String search = request.getParameter("Search");
		// System.out.println("search = " + search);
		//
		// ResultSetMetaData rsmd = null;
		// int numCols;
		// ArrayList<String> colNames = new ArrayList<String>();
		// System.out.println("dafuq");
		// try {
		// System.out.println("SELECT * FROM DTUGRP05.USERS WHERE \"UserName\"
		// LIKE '%" + search + "%' OR \"Address\" LIKE '%" + search + "%' OR
		// \"Email\" LIKE '%" + search + "%' OR \"Phone\" LIKE '%" + search +
		// "%'");
		// rs = statement.executeQuery("SELECT * FROM DTUGRP05.USERS");
		// System.out.println("hej");
		// rsmd = rs.getMetaData();
		// numCols = rsmd.getColumnCount();
		// for (int i = 1; i <= numCols; i++) {
		// colNames.add(rsmd.getColumnName(i));
		// }
		// PrintWriter out = response.getWriter();
		// out.println("<table><thead><tr>");
		// for (String name : colNames) {
		// out.println("<td>"+name+"</td>");
		// }
		// out.println("</tr></thead>");
		// out.println("<tbody>");
		// while(rs.next()) {
		// out.println("<tr>");
		// for (int i = 1; i <= numCols; i++) {
		// out.println("<td>"+rs.getString(i)+"</td>");
		// }
		// out.println("</tr>");
		// }
		// out.println("</tbody></table>");
		// } catch (SQLException e) {
		// System.out.println("fuck lortet");
		// e.printStackTrace();
		// }
		//
		//
=======
		

		if(results != null && !results.isEmpty()){
			//Create one long string of IDs for SQL
			String IDs = "(";
			for (int i = 0; i<results.size()-1; i++){
				IDs += "'"+results.get(i)+"'"+",";
			}
			IDs += "'"+results.get(results.size()-1)+"')";
			
			String message = "Search results for: "+ input;
			request.setAttribute("message", message);
			request.setAttribute("resultlist", IDs);
			request.getRequestDispatcher("search.jsp").forward(request, response);
		} else {
			String message = "Nothing matched your search terms";
			request.setAttribute("message", message);
			request.getRequestDispatcher("search.jsp").forward(request, response);
		}
>>>>>>> branch 'master' of https://github.com/anhvann/fagprojekt.git
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request, response);
	}

}
