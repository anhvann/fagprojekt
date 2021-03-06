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
	private String clientCPR = "2309911234";
	private String action = "changeaccount";
	private String accountID = "85327386530327";
	private String accountName = "Shopping";
	private String interest = "0.010";
	private BigDecimal interestBD = new BigDecimal(interest);
	private String currency;

	/*
	 * Precondition: Employee account: CPR: 9876543219 Password: vanvan Client
	 * account: CPR: 2309911234 Password: daisy2 Initial account: ID:
	 * 85327386530327 Name: Shopping Interest: 0.010 Currency: DDK
	 */
	@Before
	public void loginAndReset() throws ServletException, IOException, ClassNotFoundException, SQLException {
		// Login
		String cpr = "9876543219";
		String password = "vanvan";

		Login login = new Login();
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getSession().getAttribute("loggedinuser")).thenReturn(cpr);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		when(session.getAttribute("role")).thenReturn(login.getRole());
		accountActivityServlet = new AccountActivity();

		db = new Database(session);

		// Reset account to initial values
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

	/*Employee is logged in and successfully edits an
	 * The account's new name should be "Transportation"
	 * The account's new interest should be 0.020
	 * The returned message from the database should be "Account Edited"*/
	@Test
	public void testEditAccount() throws Exception {
		accountName = "Transportation";
		interest = "0.020";
		interestBD = new BigDecimal(interest);

		when(request.getParameter("accountName")).thenReturn(accountName);
		when(request.getParameter("interest")).thenReturn(interest);
		accountActivityServlet.doPost(request, response);

		Account account = db.getAccount(accountID); // Get updated account
		assertEquals("Account Edited", accountActivityServlet.getMessage());
		assertEquals(accountName, account.getName());
		assertEquals(new BigDecimal(interest), account.getInterest());
	}
	
	//The following scenarios cannot occur with the current interface but has been covered for security reasons
	
	/*An employee not logged in cannot edit an account
	 * The account's name after the attempt should be the same as before: "Shopping"
	 * The account's interest after the attempt should be the same as before: 0.010
	 * The returned message should be "Illegal action"*/
	@Test
	public void testNotLoggedIn() throws Exception {
		String newaccountName = "Transportation";
		String newinterest = "0.020";
		
		when(request.getSession().getAttribute("loggedinuser")).thenReturn(null);
		when(request.getParameter("accountName")).thenReturn(newaccountName);
		when(request.getParameter("interest")).thenReturn(newinterest);
		accountActivityServlet.doPost(request, response);

		Account account = db.getAccount(accountID); // Get updated account
		assertEquals("Illegal action", accountActivityServlet.getMessage());
		assertEquals(accountName, account.getName());
		assertEquals(new BigDecimal(interest), account.getInterest());
	}
	
	/*A client cannot edit an account
	 * The account's name after the attempt should be the same as before: "Shopping"
	 * The account's interest after the attempt should be the same as before: 0.010
	 * The returned message should be "Illegal action"*/
	@Test
	public void testEditAsClient() throws Exception {
		String newaccountName = "Transportation";
		String newinterest = "0.020";
		
		when(session.getAttribute("role")).thenReturn("c");
		when(request.getParameter("accountName")).thenReturn(newaccountName);
		when(request.getParameter("interest")).thenReturn(newinterest);
		accountActivityServlet.doPost(request, response);

		Account account = db.getAccount(accountID); // Get updated account
		assertEquals("Illegal action", accountActivityServlet.getMessage());
		assertEquals(accountName, account.getName());
		assertEquals(new BigDecimal(interest), account.getInterest());
	}
}
