package servlets;

import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Database;
import model.User;

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
				case "deposit" :
					request.getRequestDispatcher("cdeposit.jsp").forward(request, response);
					break;
				case "withdraw" :
					request.getRequestDispatcher("cwithdraw.jsp").forward(request, response);
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
