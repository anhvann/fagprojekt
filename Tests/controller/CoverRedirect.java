//For redirect to user interface
package controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import model.Database;

public class CoverRedirect {
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private Confirmation confirmation = new Confirmation();
	private String accountID = "85327386530327";

	/*
	 * Precondition: Employee CPR: 1234567891
	 * 
	 * Note: Employee login is tested before Confirmation is called
	 */
	@Before
	public void initialize() {
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("accountID")).thenReturn(accountID);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	}

	@Test
	public void testTransactionSuccess() throws Exception {
		String message = "Successful Transaction"; //any success string
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testDepositInvalidAccount() throws Exception {
		String message = "Deposit Invalid Account";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testWithdrawInvalidAccount() throws Exception {
		String message = "Withdraw Invalid Account";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);

	}

	@Test
	public void testWithdrawInsufficientBalance() throws Exception {
		String message = "Withdraw Insufficient Balance";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testTransferInsufficientBalance() throws Exception {
		String message = "Transfer Insufficient Balance";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testEmployeeTransferInvalidAccount() throws Exception {
		String message = "Transfer Invalid Account";
		when(session.getAttribute("role")).thenReturn("e");
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testClientTransferInvalidAccount() throws Exception {
		String message = "Transfer Invalid Account";
		when(session.getAttribute("role")).thenReturn("c");
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testEmployeeTransferSameAccount() throws Exception {
		String message = "Same account failure";
		when(session.getAttribute("role")).thenReturn("e");
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}

	@Test
	public void testClientTransferSameAccount() throws Exception {
		String message = "Same account failure";
		when(session.getAttribute("role")).thenReturn("c");
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testViewClientTransferPage() throws Exception {
		ClientActivity clientActivityServlet = new ClientActivity();
		when(request.getParameter("ID")).thenReturn("2309911234");
		clientActivityServlet.doPost(request, response);
	}
}
