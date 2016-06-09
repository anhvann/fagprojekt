package controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import model.*;

public class TestCreateDeleteAccount {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private AccountActivity accountActivityServlet;
	private Database db;
	private String clientCPR = "2309911234";
	private String accountID;
	private String action = "createaccount";
	private String currency;
	
	/*Precondition:
	 * Employee account:
	 * 		CPR: 9876543219
	 * 		Password: vanvan
	 * Client account:
	 * 		ID: 2309911234	
	 * 		Account 85327386530327 has balance > 0 DKK
	 * Account 00000000000000 does not exist
	*/
	@Before
	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException{
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
	}
	@Test
	public void testCreateDeleteAccountSuccess() throws Exception {
		//Create
		String name = "Extra";
		String interest = "0.010";
		String currency = "DKK";
		BigDecimal interestBD = new BigDecimal(interest);
		
		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		
		when(request.getParameter("ID")).thenReturn(clientCPR);
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn("");
		when(request.getParameter("accountName")).thenReturn(name);
		when(request.getParameter("interest")).thenReturn(interest);
		when(request.getParameter("ISOCode")).thenReturn(currency);
		accountActivityServlet.doPost(request, response);
		
		user = db.getUser(clientCPR); //get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertNotNull(db.getAccount(accountActivityServlet.getAccountID()));
		assertEquals(1, numberOfAccountsAfter-numberOfAccountsBefore);
		assertEquals("Success in Creating Account", accountActivityServlet.getMessage());
		
		//Delete
		accountID = accountActivityServlet.getAccountID();
		action = "closeaccount";
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);
		
		db.getUser(clientCPR); //get updated user
		int numberOfAccountsFinal = db.getAccounts(user).size();
		assertNull(db.getAccount(accountActivityServlet.getAccountID()));
		assertEquals(-1, numberOfAccountsFinal-numberOfAccountsAfter);
		assertEquals("Account deleted", accountActivityServlet.getMessage());
	}
	
	@Test
	public void testDeleteAccountWithMoney() throws NullPointerException, ServletException, IOException {
		accountID = "85327386530327";
		action = "closeaccount";
		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);	
		
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(numberOfAccountsBefore, numberOfAccountsAfter);
		
		assertEquals("Cannot delete because account has money", accountActivityServlet.getMessage());	
	}
	
	@Test
	public void testDeleteNonExistentAccount() throws NullPointerException, ServletException, IOException {
		accountID = "00000000000000";
		action = "closeaccount";

		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);	
		
		assertEquals("Invalid account", accountActivityServlet.getMessage());	
	}
	
	//Not possible through user interface
	@Test
	public void testCreateInvalidISOCode() throws NullPointerException, ServletException, IOException {
		//Create
		String name = "Extra";
		String interest = "0.010";
		String currency = "DKR";
		BigDecimal interestBD = new BigDecimal(interest);
		
		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		
		when(request.getParameter("ID")).thenReturn(clientCPR);
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("accountName")).thenReturn(name);
		when(request.getParameter("interest")).thenReturn(interest);
		when(request.getParameter("ISOCode")).thenReturn(currency);
		accountActivityServlet.doPost(request, response);
		
		user = db.getUser(clientCPR); //get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter-numberOfAccountsBefore);
		assertEquals("Invalid ISO-Code", accountActivityServlet.getMessage());		
	}
	
	// For user interface
		@Test
		public void testViewNewAccountPage() throws Exception {
			action = "newaccount";
			when(request.getParameter("ID")).thenReturn(clientCPR);
			when(request.getParameter("action")).thenReturn(action);
			accountActivityServlet.doPost(request, response);
		}
}