package controller;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.sql.SQLException;
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
	private String name = "Extra";
	private String interest = "0.010";
	private String currency = "DKK";

	/*
	 * Precondition: 
	 * 	Employee account: 
	 * 		CPR: 9876543219 
	 * 		Password: vanvan 
	 * 	Client account: 
	 * 		ID: 2309911234 
	 * 		Account: 85327386530327 has balance > 0 DKK
	 * 		Account: 85327386530333 has balance = 0
	 * Account 00000000000000 does not exist
	 */
	@Before
	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException {
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
		when(request.getParameter("ID")).thenReturn(clientCPR);
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountName")).thenReturn(name);
		when(request.getParameter("interest")).thenReturn(interest);
		when(request.getParameter("ISOCode")).thenReturn(currency);
	}

	@Test
	/*Employee is logged in and successfully creates and deletes an account
	 * Create:
	 * Getting the account from the database should not return null
	 * The client should have one more account
	 * The returned message from the database should be "Success in Creating Account"
	 * Delete:
	 * Getting the account from the database should return null
	 * The client should have one account less
	 * The returned message from the database should be "Account deleted"*/
	public void testCreateDeleteAccountSuccess() throws Exception {
		// Create
		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		accountActivityServlet.doPost(request, response);
		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertNotNull(db.getAccount(accountActivityServlet.getAccountID()));
		assertEquals(1, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Success in Creating Account", accountActivityServlet.getMessage());

		// Delete
		accountID = accountActivityServlet.getAccountID();
		when(request.getParameter("action")).thenReturn("closeaccount");
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);

		db.getUser(clientCPR); // get updated user
		int numberOfAccountsFinal = db.getAccounts(user).size();
		assertNull(db.getAccount(accountActivityServlet.getAccountID()));
		assertEquals(-1, numberOfAccountsFinal - numberOfAccountsAfter);
		assertEquals("Account deleted", accountActivityServlet.getMessage());
	}

	/*Account 85327386530333 has balance = 0, so attempting to delete it results in failure
	 * The number of accounts of the client should be the same before and after the attempt
	 * The returned message from the database should be "Cannot delete because account has money"*/
	@Test
	public void testDeleteAccountWithMoney() throws NullPointerException, ServletException, IOException {
		action = "closeaccount";
		accountID = "85327386530327";

		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);

		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Cannot delete because account has money", accountActivityServlet.getMessage());
	}

	/*Account 00000000000000 does not exist, so attempting to delete it results in failure
	 * The returned message from the database should be "Invalid account"*/
	@Test
	public void testDeleteNonExistentAccount() throws NullPointerException, ServletException, IOException {
		action = "closeaccount";
		accountID = "00000000000000";
		
		when(request.getParameter("action")).thenReturn(action);
		when(request.getParameter("accountID")).thenReturn(accountID);
		accountActivityServlet.doPost(request, response);
		
		assertEquals("Invalid account", accountActivityServlet.getMessage());
	}

	// The following scenarios cannot occur with the current interface but has been covered for security reasons
	
	/*The ISO code is not registered in the database so no acconut can be created with it as default currency
	 * The number of accounts of the client should be the same before and after the attempt
	 * The returned message from the database should be "Invalid ISO-Code"*/
	@Test
	public void testCreateInvalidISOCode() throws NullPointerException, ServletException, IOException {
		String currency = "DKR";

		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		when(request.getParameter("ISOCode")).thenReturn(currency);
		accountActivityServlet.doPost(request, response);

		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Invalid ISO-Code", accountActivityServlet.getMessage());
	}

	/*Employees who are not logged in cannot create an account
	 * The number of accounts of the client should be the same before and after the attempt
	 * The returned message should be "Illegal action"*/
	@Test
	public void testCreateNotLoggedIn() throws NullPointerException, ServletException, IOException {
		when(request.getSession().getAttribute("loggedinuser")).thenReturn(null);

		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		accountActivityServlet.doPost(request, response);

		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}

	/*A client cannot create an account
	 * The number of accounts of the client should be the same before and after the attempt
	 * The returned message should be "Illegal action"*/
	@Test
	public void testCreateLoggedInClient() throws NullPointerException, ServletException, IOException {
		when(request.getSession().getAttribute("role")).thenReturn("c");

		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		accountActivityServlet.doPost(request, response);

		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
	
	/*Employees who are not logged in cannot delete an account
	 * * The number of accounts of the client should be the same before and after the attempt
	 * The returned message should be "Illegal action"*/
	@Test
	public void testDeleteNotLoggedIn() throws Exception {
		accountID = accountActivityServlet.getAccountID();
		when(request.getParameter("action")).thenReturn("closeaccount");
		when(request.getParameter("accountID")).thenReturn("85327386530333");
		when(request.getSession().getAttribute("loggedinuser")).thenReturn(null); //log out
		accountActivityServlet.doPost(request, response);

		db.getUser(clientCPR); // get updated user
		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
	
	/*A client cannot delete an account
	 * The number of accounts of the client should be the same before and after the attempt
	 * The returned message should be "Illegal action"*/
	@Test
	public void testDeleteAsClient() throws Exception {
		when(request.getParameter("action")).thenReturn("closeaccount");
		when(request.getParameter("accountID")).thenReturn("85327386530333");
		when(request.getSession().getAttribute("role")).thenReturn("c"); //log in as client
		accountActivityServlet.doPost(request, response);
		
		User user = db.getUser(clientCPR);
		int numberOfAccountsBefore = db.getAccounts(user).size();
		accountActivityServlet.doPost(request, response);

		user = db.getUser(clientCPR); // get updated user
		int numberOfAccountsAfter = db.getAccounts(user).size();
		assertEquals(0, numberOfAccountsAfter - numberOfAccountsBefore);
		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
}