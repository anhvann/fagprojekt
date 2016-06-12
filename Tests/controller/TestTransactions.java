package controller;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.junit.*;
import model.*;

public class TestTransactions {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private TransactionActivity transactionServlet;
	private Database db;
	private String clientCPR = "2309911234";
	private String accountID1 = "85327386530327"; //DKK
	private String accountID2 = "85327386530329"; //DDK
	private String accountID3 = "85327386530331"; //DDK
	private String CPR3 = "0306628962";
	private String accountGBP = "85327386530328";
	
	/*Precondition: 
	 * Employee account:
	 * 		CPR: 9876543219
	 * 		Password: vanvan
	 * 
	 * Account 85327386530327 may not have more than 1,000,000 DKK
	 * Account 85327386530327 may not have less than 400 DDK
	 * Account 85327386530331 may not have less than 100 DDK
	 * Account 00000000000000 does not exist
	 */
	
	@Before
	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException{
		String cpr = "9876543219";
		String password = "vanvan";

		Login login = new Login();
	    when(request.getSession()).thenReturn(session);
	    when(request.getParameter("cpr")).thenReturn(cpr);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	    
	    login.doPost(request, response);
	    when(session.getAttribute("role")).thenReturn(login.getRole());
	    when(session.getAttribute("loggedinuser")).thenReturn(login.getLoggedInUser());
		transactionServlet = new TransactionActivity();
		
		db = new Database(session);
	}
	
	@Test
	public void testDepositSuccess() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("400");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Deposit completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("400");
	    assertEquals(balanceOld.add(amount), balanceNew);
	}

	@Test
	public void testDepositSuccess2() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID3).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn(accountID3);
	    when(request.getParameter("amount")).thenReturn("50");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(CPR3);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID3).getBalance();
	    assertEquals("Deposit completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("50");
	    assertEquals(balanceOld.add(amount), balanceNew);
	    
	    
	}
	
	@Test
	public void testDepositDifferentCurrency() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Deposit completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("73.30");
	    assertEquals(balanceOld.add(amount), balanceNew);
	}
	
	@Test
	public void testDepositInvalidAccount() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn("0000000000000");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Deposit Invalid Account", transactionServlet.getMessage());
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testWithdrawSuccess() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Withdraw completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("100");
	    assertEquals(balanceOld.subtract(amount), balanceNew);
	}
	
	@Test
	public void testWithdrawDifferentCurrency() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Withdraw completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("75.70");
	    assertEquals(balanceOld.subtract(amount), balanceNew);
	}
	
	@Test
	public void testWithdrawInvalidAccount() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn("00000000000000");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Withdraw Invalid Account",transactionServlet.getMessage());
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testWithdrawInvalidAmount() throws Exception {
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("1000001");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    assertEquals("Withdraw Insufficient Balance", transactionServlet.getMessage());
	}
	
	@Test
	public void testTransferSuccess() throws Exception {
		BigDecimal balanceOld1 = db.getAccount(accountID1).getBalance();
		BigDecimal balanceOld2 = db.getAccount(accountID2).getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn(accountID2);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount(accountID1).getBalance();
	    BigDecimal balanceNew2 = db.getAccount(accountID2).getBalance();
	    assertEquals("Transfer completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("100");
	    assertEquals(balanceOld1.subtract(amount), balanceNew1);
	    assertEquals(balanceOld2.add(amount), balanceNew2);
	}
	@Test
	public void testTransferSuccess2() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID2).getBalance();
		
		//First Transfer
		when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID3);
	    when(request.getParameter("accountID2")).thenReturn(accountID2);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    assertEquals("Transfer completed", transactionServlet.getMessage());

	    //Second transfer
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn(accountID2);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("20");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(CPR3);
	    transactionServlet.doPost(request, response);
	    assertEquals("Transfer completed", transactionServlet.getMessage());
	    
	    BigDecimal balanceNew = db.getAccount(accountID2).getBalance();
	    
	    BigDecimal amount = new BigDecimal("30");
	    assertEquals(balanceOld.add(amount), balanceNew);
	}
	@Test
	public void testTransferDifferentCurrency() throws Exception {
		BigDecimal balanceOld1 = db.getAccount(accountID1).getBalance();
		BigDecimal balanceOld2 = db.getAccount(accountGBP).getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn(accountGBP);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount(accountID1).getBalance();
	    BigDecimal balanceNew2 = db.getAccount(accountGBP).getBalance();
	    assertEquals("Transfer completed", transactionServlet.getMessage());
	    BigDecimal amount = new BigDecimal("75.70");
	    BigDecimal amount2 = new BigDecimal("7.80");
	    assertEquals(balanceOld1.subtract(amount), balanceNew1);
	    assertEquals(balanceOld2.add(amount2), balanceNew2);
	}
	
	@Test
	public void testTransferInvalidAccount() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn("00000000000000");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Transfer Invalid Account", transactionServlet.getMessage());
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testTransferInvalidAccount2() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID2).getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("00000000000000");
	    when(request.getParameter("accountID2")).thenReturn(accountID2);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID2).getBalance();
	    assertEquals("Transfer Invalid Account", transactionServlet.getMessage()); //Missing message
	    assertEquals(balanceOld, balanceNew);
	}
	@Test
	public void testTransferInvalidAmount() throws Exception {
		BigDecimal balanceOld1 = db.getAccount(accountID1).getBalance();
		BigDecimal balanceOld2 = db.getAccount(accountID2).getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("accountID2")).thenReturn(accountID2);
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("1000000");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount(accountID1).getBalance();
	    BigDecimal balanceNew2 = db.getAccount(accountID2).getBalance();
	    assertEquals("Transfer Insufficient Balance", transactionServlet.getMessage());
	    assertEquals(balanceOld1, balanceNew1);
	    assertEquals(balanceOld2, balanceNew2);
	}
	
	//Not possible through user interface
	@Test
	public void testInvalidAmount() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("hundred");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Invalid amount", transactionServlet.getMessage());
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testInvalidAction() throws Exception {
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("depo");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals(balanceOld, balanceNew);
	}
	@Test
	public void testNotLoggedIn() throws Exception {
	    when(request.getSession()).thenReturn(null);
		BigDecimal balanceOld = db.getAccount(accountID1).getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn(accountID1);
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn(clientCPR);
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount(accountID1).getBalance();
	    assertEquals("Illegal action", transactionServlet.getMessage());
	    assertEquals(balanceOld, balanceNew);
	}
}
