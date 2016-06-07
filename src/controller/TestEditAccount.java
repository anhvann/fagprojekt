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
import org.junit.rules.ExpectedException;

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
	private String clientCPR = "1234512345";
	private String action = "changeaccount";
	private String accountID = "46265417464412";
	private String accountName = "Food";
	private String interest = "0.010";
	private BigDecimal interestBD = new BigDecimal(interest);
	private String currency;
	
	/*Precondition:
	 * Employee account:
	 * 		CPR: 9876543219
	 * 		Password: vanvan
	 * Initial account:
	 * 		ID: 46265417464412
	 * 		Name: Food
	 * 		Interest: 0.010
	 * 		Currency: DDK 		
	*/
	@Before
	public void loginAndReset() throws ServletException, IOException, ClassNotFoundException, SQLException{
		//Login
		String cpr = "9876543219";
		String password = "vanvan";

		Login login = new Login();
	    when(request.getSession()).thenReturn(session);
	    when(request.getParameter("cpr")).thenReturn(cpr);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

	    login.doPost(request, response);
	    when(session.getAttribute("role")).thenReturn(login.getRole());
	    accountActivityServlet = new AccountActivity();
		
		db = new Database(session);
		
		//Reset account to initial values
		currency = db.getAccount(accountID).getISOCode();

	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("accountID")).thenReturn(accountID);
	    when(request.getParameter("accountName")).thenReturn(accountName);
	    when(request.getParameter("interest")).thenReturn(interest);
	    when(request.getParameter("ISOCode")).thenReturn(currency);
	    accountActivityServlet.doPost(request, response);
	    
	    Account account = db.getAccount(accountID);
		assertEquals(accountName, account.getName());
		assertEquals(interestBD, account.getInterest());
	}
	@Test
	public void testEditAccount() throws Exception {
		accountName = "Transportation";
		interest = "0.020";
		interestBD = new BigDecimal(interest);
		
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("accountID")).thenReturn(accountID);
	    when(request.getParameter("accountName")).thenReturn(accountName);
	    when(request.getParameter("interest")).thenReturn(interest);
	    when(request.getParameter("ISOCode")).thenReturn(currency);
	    accountActivityServlet.doPost(request, response);
		
		Account account = db.getAccount(accountID); //Get updated account
		assertEquals(accountName, account.getName());
		assertEquals(interestBD, account.getInterest());
	}
	
	//Not possible through interface
	@Test
	public void testNotLoggedIn() throws Exception {
		//Log out
		when(request.getSession()).thenReturn(null);
		
		//Test
		String newAccountName = "Transportation";
		interest = "0.020";
		
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("accountID")).thenReturn(accountID);
	    when(request.getParameter("accountName")).thenReturn(newAccountName);
	    when(request.getParameter("interest")).thenReturn(interest);
	    when(request.getParameter("ISOCode")).thenReturn(currency);
	    accountActivityServlet.doPost(request, response);
		
		Account account = db.getAccount(accountID); //Get updated account
		assertEquals(accountName, account.getName());
		assertEquals(interestBD, account.getInterest());
	}
	
	@Test
	public void testLoggedInAsClient() throws Exception {
		//Log in
		String cpr = "1234512345";
		String password = "c123";

		Login login = new Login();
	    when(request.getSession()).thenReturn(session);
	    when(request.getParameter("cpr")).thenReturn(cpr);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

	    login.doPost(request, response);
	    when(session.getAttribute("role")).thenReturn(login.getRole());
	    accountActivityServlet = new AccountActivity();
		
		db = new Database(session);
		
		//Test
		String newAccountName = "Transportation";
		interest = "0.020";
		
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("accountID")).thenReturn(accountID);
	    when(request.getParameter("accountName")).thenReturn(newAccountName);
	    when(request.getParameter("interest")).thenReturn(interest);
	    when(request.getParameter("ISOCode")).thenReturn(currency);
	    accountActivityServlet.doPost(request, response);
		
		Account account = db.getAccount(accountID); //Get updated account
		assertEquals(accountName, account.getName());
		assertEquals(interestBD, account.getInterest());
	}	
}
