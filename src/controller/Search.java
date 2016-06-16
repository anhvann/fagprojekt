package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Database;
import model.User;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LinkedList<User> results = new LinkedList<>();
	Database db = null;
	
	public Search() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = request.getParameter("searchfield");
		
		try {
			db = new Database(request.getSession());
			results = db.searchFor(input.toLowerCase());
			if(results != null && !results.isEmpty()){
				String message = "Search results for: "+ input;
				request.setAttribute("searchmessage", message);
				request.setAttribute("resultlist", results);
				request.setAttribute("toast", false);
				request.getRequestDispatcher("search.jsp").forward(request, response);
			} else {
				String message = "Nothing matched your search terms";
				request.setAttribute("searchmessage", message);
				request.setAttribute("toast", false);
				request.getRequestDispatcher("search.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doGet(request, response);
	}

	public LinkedList<User> getResults(){
		return results;
	}
}
