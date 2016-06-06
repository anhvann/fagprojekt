package controller;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;

import model.Account;
import model.Database;
import model.Transaction;

public class TestEditAccount {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private AccountActivity accountActivityServlet;
	private Database db;
	
	/*Precondition:
	 * Initial account:
	 * 		ID: 46265417464412 (not unchangeable)
	 * 		Name: Food
	 * 		Interest: 0.01
	 * 		Currency: DDK
	 * 		
	*/
	@Before
	public void loginAndReset() throws ServletException, IOException, ClassNotFoundException, SQLException{
		String cpr = "9876543219";
		String password = "vanvan";

		Login login = new Login();
	    when(request.getSession()).thenReturn(session);
	    when(request.getParameter("cpr")).thenReturn(cpr);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

	    login.doPost(request, response);
	    when(session.getAttribute("role")).thenReturn(login.getLoggedInUser());
	    accountActivityServlet = new AccountActivity();
		
		db = new Database(session);
		
		//Reset account to initial values
		String clientCPR = "1234512345";
		String accountID = "46265417464412";
		String name = "Food";
		String interest = "0.01";
		String currency = "DDK";

	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    when(request.getParameter("action")).thenReturn("changeaccount");
	    when(request.getParameter("accountID")).thenReturn(accountID);
	    when(request.getParameter("name")).thenReturn("");
	    when(request.getParameter("interest")).thenReturn(interest);
	    when(request.getParameter("ISOCode")).thenReturn(""); //Change ISO?
	    when(request.getParameter("accountName")).thenReturn(name);
		accountActivityServlet.doPost(request, response);
	}
	@Test
	public void testEditAccount() throws Exception {
		String cpr = "1234512345";
		String accountID = "46265417464412";
		String name = "Transportation";
		String interest = "0.02";
		String currency = "DDK";

	}	
}
