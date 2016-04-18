//package fagprojekt;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import Controller.BankApp;
//
///**
// * Servlet implementation class Search
// */
//@WebServlet("/Search")
//public class Search extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	BankApp bankApp = null;
//	
//	public Search() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (bankApp == null) {
//			bankApp = new BankApp();
//		}
//		String input = request.getParameter("searchfield");
//		try {
//			bankApp.searchFor(input);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//}
