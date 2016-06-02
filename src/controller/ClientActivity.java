package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.*;
/**
 * Servlet implementation class ClientActivity
 */
@WebServlet("/ClientActivity")
public class ClientActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClientActivity() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpr = request.getParameter("ID");
		String action = request.getParameter("action");
		try {
			Database db = new Database();
			User user = db.getUser(cpr);
			String message;
			request.setAttribute("accounts", user.getAccounts());
			request.setAttribute("fullname", user.getName());
			request.setAttribute("cpr", cpr);
			switch (action) {
				case "transfer" :
					request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
					break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
