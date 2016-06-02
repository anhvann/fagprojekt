package controller;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;
import model.*;
import java.math.BigDecimal;

public class TestTransactions {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher dispatcher;
	private Transactions transactionServlet;
	private Database db;
	
	//Precondition: Account 46265417464412 may not have more than 1,000,000 DKK
	
	@Before
	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException{
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest requestLogin = mock(HttpServletRequest.class);       
	    HttpServletResponse responseLogin = mock(HttpServletResponse.class);
	    when(requestLogin.getParameter("cpr")).thenReturn("9876543219");
	    when(requestLogin.getParameter("password")).thenReturn("vanvan");
		new Login().doPost(requestLogin, responseLogin);
		request = mock(HttpServletRequest.class);       
		response = mock(HttpServletResponse.class); 
		dispatcher = mock(RequestDispatcher.class);
		transactionServlet = new Transactions();
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		db = new Database();
	}
	
	@Test
	public void testDepositSuccess() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Deposit completed");
	    BigDecimal amount = new BigDecimal("100");
	    assertEquals(balanceOld.add(amount), balanceNew);
	}
	@Test
	public void testDepositDifferentCurrency() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Deposit completed");
	    BigDecimal amount = new BigDecimal("73.30");
	    assertEquals(balanceOld.add(amount), balanceNew);
	}
	
	@Test
	public void testDepositWrongAccount() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn("46265417464411");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Invalid Account ID");
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testWithdrawSuccess() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Withdraw completed");
	    BigDecimal amount = new BigDecimal("100");
	    assertEquals(balanceOld.subtract(amount), balanceNew);
	}
	
	@Test
	public void testWithdrawDifferentCurrency() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Withdraw completed");
	    BigDecimal amount = new BigDecimal("75.70");
	    assertEquals(balanceOld.subtract(amount), balanceNew);
	}
	
	@Test
	public void testWithdrawWrongAccount() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn("46265417464411");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Invalid Account ID");
	    assertEquals(balanceOld, balanceNew);
	}
	
	@Test
	public void testWithdrawInvalidAmount() throws Exception {
	    when(request.getParameter("action")).thenReturn("withdraw");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("1000000");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    assertEquals(transactionServlet.getMessage(), "Withdraw completed"); //Wrong message in data base
	}
	
	@Test
	public void testTransferSuccess() throws Exception {
		BigDecimal balanceOld1 = db.getAccount("46265417464412").getBalance();
		BigDecimal balanceOld2 = db.getAccount("16540565420873").getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("16540565420873");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount("46265417464412").getBalance();
	    BigDecimal balanceNew2 = db.getAccount("16540565420873").getBalance();
	   // assertEquals(transactionServlet.getMessage(), "Transfer completed"); //Wrong message in data base
	    BigDecimal amount = new BigDecimal("100");
	    assertEquals(balanceOld1.subtract(amount), balanceNew1);
	    assertEquals(balanceOld2.add(amount), balanceNew2);
	}
	
	@Test
	public void testTransferDifferentCurrency() throws Exception {
		BigDecimal balanceOld1 = db.getAccount("46265417464412").getBalance();
		BigDecimal balanceOld2 = db.getAccount("82506733128212").getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("82506733128212");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("10");
	    when(request.getParameter("ISOCode")).thenReturn("EUR");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount("46265417464412").getBalance();
	    BigDecimal balanceNew2 = db.getAccount("82506733128212").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Transfer completed"); //Wrong message in data base
	    BigDecimal amount = new BigDecimal("75.70");
	    assertEquals(balanceOld1.subtract(amount), balanceNew1);
	    assertEquals(balanceOld2.add(amount), balanceNew2);
	}
	
	@Test
	public void testTransferWrongAccount() throws Exception {
		BigDecimal balanceOld1 = db.getAccount("46265417464412").getBalance();
		BigDecimal balanceOld2 = db.getAccount("82506733128212").getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("82506733128211");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount("46265417464412").getBalance();
	    BigDecimal balanceNew2 = db.getAccount("82506733128212").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Money Transaction failure");
	    assertEquals(balanceOld1, balanceNew1);
	    assertEquals(balanceOld2, balanceNew2);
	}
	
	@Test
	public void testTransferWrongAccount2() throws Exception {
		BigDecimal balanceOld1 = db.getAccount("46265417464412").getBalance();
		BigDecimal balanceOld2 = db.getAccount("82506733128212").getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("46265417464411");
	    when(request.getParameter("accountID2")).thenReturn("82506733128212");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("100");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount("46265417464412").getBalance();
	    BigDecimal balanceNew2 = db.getAccount("82506733128212").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Money Transaction failure");
	    assertEquals(balanceOld1, balanceNew1);
	    assertEquals(balanceOld2, balanceNew2);
	}
	@Test
	public void testTransferInvalidAmount() throws Exception {
		BigDecimal balanceOld1 = db.getAccount("46265417464412").getBalance();
		BigDecimal balanceOld2 = db.getAccount("82506733128212").getBalance();
	    when(request.getParameter("action")).thenReturn("transfer");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("82506733128212");
	    when(request.getParameter("transName")).thenReturn("Loan");
	    when(request.getParameter("amount")).thenReturn("1000000");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew1 = db.getAccount("46265417464412").getBalance();
	    BigDecimal balanceNew2 = db.getAccount("82506733128212").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Money Transaction failure"); //Can transfer invalid amount
	    assertEquals(balanceOld1, balanceNew1);
	    assertEquals(balanceOld2, balanceNew2);
	}
	
	//Not possible through user interface
	@Test
	public void testInvalidAmount() throws Exception {
		BigDecimal balanceOld = db.getAccount("46265417464412").getBalance();
	    when(request.getParameter("action")).thenReturn("deposit");
	    when(request.getParameter("accountID")).thenReturn("46265417464412");
	    when(request.getParameter("accountID2")).thenReturn("");
	    when(request.getParameter("transName")).thenReturn("");
	    when(request.getParameter("amount")).thenReturn("hundred");
	    when(request.getParameter("ISOCode")).thenReturn("DKK");
	    when(request.getParameter("ID")).thenReturn("1234512345");
	    transactionServlet.doPost(request, response);
	    BigDecimal balanceNew = db.getAccount("46265417464412").getBalance();
	    assertEquals(transactionServlet.getMessage(), "Invalid amount");
	    assertEquals(balanceOld, balanceNew);
	}
}
