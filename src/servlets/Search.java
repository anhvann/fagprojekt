package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Database;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Database db = null;
	
	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = request.getParameter("searchfield");
		LinkedList<String> results = null;
		
		try {
			db = new Database();
			results = db.searchFor(input.toLowerCase());
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
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request, response);
	}

}
