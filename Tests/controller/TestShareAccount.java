package controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;


import com.ibm.db2.jcc.t4.SysplexGroupStatistics;

import model.Database;

public class TestShareAccount {
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private AccountActivity accountActivityServlet;
	private Database db;
	private String clientCPROwner = "2309911234";
	private String clientCPR = "3112261111";
	private String accountID = "85327386530334";

	/*
	 * Precondition: 
	 * 	Employee: 
	 * 		CPR: 9876543219 
	 * 		Password: vanvan 
	 * User 3112261111 is not an owner of account 85327386530334 
	 * No user has CPR number: 0000000000
	 * User 2309911234 and user 3112261111 share account 85327386530339
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
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		when(session.getAttribute("role")).thenReturn(login.getRole());
		accountActivityServlet = new AccountActivity();

		db = new Database(session);
	}

	@Test
	public void testAddRemoveOwnerSuccess() throws Exception {
		// Add
		int ownersBefore = db.getOwners(accountID).size();
		int accountsBefore = db.getAccounts(db.getUser(clientCPR)).size();
		when(request.getParameter("action")).thenReturn("share");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn(clientCPR);
		accountActivityServlet.doPost(request, response);

		assertEquals("Ownership added", accountActivityServlet.getMessage());
		int ownersAfter = db.getOwners(accountID).size();
		int accountsAfter = db.getAccounts(db.getUser(clientCPR)).size();
		
		assertEquals(1, ownersAfter - ownersBefore);
		assertEquals(1, accountsAfter - accountsBefore);

		// Remove
		when(request.getParameter("action")).thenReturn("deleteowner");
		accountActivityServlet.doPost(request, response);

		assertEquals("Ownership removed", accountActivityServlet.getMessage());
		int ownersFinal = db.getOwners(accountID).size();
		int accountFinal = db.getAccounts(db.getUser(clientCPR)).size();
		
		assertEquals(-1, ownersFinal - ownersAfter);
		assertEquals(-1, accountFinal - accountsAfter);
	}

	@Test
	public void testAddOwnerWhoIsAlreadyOwner() throws Exception {
		// Add
		int ownersBefore = db.getOwners(accountID).size();
		int accountsBefore = db.getAccounts(db.getUser(clientCPROwner)).size();
		when(request.getParameter("action")).thenReturn("share");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn(clientCPROwner);
		accountActivityServlet.doPost(request, response);

		assertEquals("Ownership already exists", accountActivityServlet.getMessage());
		int ownersAfter = db.getOwners(accountID).size();
		int accountsAfter = db.getAccounts(db.getUser(clientCPROwner)).size();
		;
		assertEquals(0, ownersAfter - ownersBefore);
		assertEquals(0, accountsAfter - accountsBefore);
	}

	@Test
	public void testAddNonExistentOwner() throws Exception {
		// Add
		int ownersBefore = db.getOwners(accountID).size();
		when(request.getParameter("action")).thenReturn("share");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn("0000000000");
		accountActivityServlet.doPost(request, response);

		assertEquals("Invalid User", accountActivityServlet.getMessage());
		int ownersAfter = db.getOwners(accountID).size();
		assertEquals(0, ownersAfter - ownersBefore);
	}
	
	// For interface
	@Test
	public void testViewAddOwnerPage() throws Exception {
		when(request.getParameter("action")).thenReturn("addowner");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn(clientCPR);
		accountActivityServlet.doPost(request, response);
	}
	
	//Not possible through user interface
	@Test
	public void testAddNotLoggedIn() throws Exception {
		when(request.getSession()).thenReturn(null);
		when(request.getParameter("action")).thenReturn("share");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn(clientCPR);
		accountActivityServlet.doPost(request, response);

		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
	@Test
	public void testAddAsClient() throws Exception {
		when(request.getSession().getAttribute("role")).thenReturn("c");
		when(request.getParameter("action")).thenReturn("share");
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getParameter("newCPR")).thenReturn(clientCPR);
		accountActivityServlet.doPost(request, response);

		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
	
	@Test
	public void testRemoveNotLoggedIn() throws Exception {
		when(request.getSession()).thenReturn(null);
		when(request.getParameter("action")).thenReturn("deleteowner");
		when(request.getParameter("accountID")).thenReturn("85327386530339");
		when(request.getParameter("newCPR")).thenReturn("3112261111");
		accountActivityServlet.doPost(request, response);

		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
	@Test
	public void testRemoveAsClient() throws Exception {
		when(request.getSession().getAttribute("role")).thenReturn("c");
		when(request.getParameter("action")).thenReturn("deleteowner");
		when(request.getParameter("accountID")).thenReturn("85327386530339");
		when(request.getParameter("newCPR")).thenReturn("3112261111");
		accountActivityServlet.doPost(request, response);

		assertEquals("Illegal action", accountActivityServlet.getMessage());
	}
}