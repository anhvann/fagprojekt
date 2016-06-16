package controller;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import model.Database;

public class TestCurrency {
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private CurrencyExchangeRates currencyServlet = new CurrencyExchangeRates();

	@Test
	public void testGetCurrencies() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		currencyServlet.doGet(request, response);
		assertEquals(26, currencyServlet.getRates().size()); //number of registered currencies excluding DKK
	}
}
