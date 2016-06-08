//For user interface
package controller;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import model.Database;

public class TestTransactionRedirect {
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private Confirmation confirmation = new Confirmation();
	private String cpr = "2309911234";
	private String accountID = "85327386530327";

	/*
	 * Preconditions: Employee: CPR: 9876543219 Password: vanvan Client: CPR:
	 * 2309911234 AccountID: 85327386530327
	 *
	 * Role check for employee is not needed since it will have been checked
	 * before the Confirmation class is called
	 */
	
	@Before
	public void initialize(){
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	    when(request.getSession()).thenReturn(session);
	    
	    when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("accountID")).thenReturn(accountID);
	}
	
	@Test
	public void testTransactionSuccess() throws NullPointerException, ServletException, IOException {
		String message = "Successful Transaction";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testDepositFailure() throws NullPointerException, ServletException, IOException {
		String message = "Deposit failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testWithdrawFailure() throws NullPointerException, ServletException, IOException {
		String message = "Withdraw failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testEmployeeTransferFailure() throws NullPointerException, ServletException, IOException {
		when(session.getAttribute("role")).thenReturn("e");
		String message = "Money Transaction failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	@Test
	public void testClientTransferFailure() throws NullPointerException, ServletException, IOException {
		when(session.getAttribute("role")).thenReturn("c");
		String message = "Money Transaction failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	@Test
	public void testEmployeeTransferToSameAccount() throws ServletException, IOException{
		when(session.getAttribute("role")).thenReturn("e");
		String message = "Same account failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	@Test
	public void testClientTransferToSameAccount() throws ServletException, IOException{
		when(session.getAttribute("role")).thenReturn("c");
		String message = "Same account failure";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
	
	//Other message (not possible)
	@Test
	public void testOtherMessage() throws ServletException, IOException{
		String message = "";
		when(request.getParameter("message")).thenReturn(message);
		confirmation.doPost(request, response);
	}
}
