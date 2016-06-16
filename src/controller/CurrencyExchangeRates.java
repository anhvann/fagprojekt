package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Currency;
import model.Database;

@WebServlet("/CurrencyExchangeRates")
public class CurrencyExchangeRates extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LinkedList<Currency> rates;

	public CurrencyExchangeRates() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Database db = new Database(request.getSession());
			rates = db.getExchangeRates();
			request.setAttribute("rates", rates);
			request.getRequestDispatcher("buysellrate.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public LinkedList<Currency> getRates(){
		return rates;
	}
}
