package controller;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;
import model.*;

public class TestDeposit {
	
	Database db;
	
	@Test
	public void testDepositSuccess() throws Exception {
		String message = "";
		String cpr = "9876543219";
		
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);    
	     when(request.getParameter("cpr")).thenReturn("9876543219");
	     when(request.getParameter("password")).thenReturn("vanvan");
	     
	     HttpServletRequest request2 = mock(HttpServletRequest.class);       
	     HttpServletResponse response2 = mock(HttpServletResponse.class);    
	     when(request2.getParameter("action")).thenReturn("deposit");
	     when(request2.getParameter("accountID")).thenReturn("1234512345");
	     when(request2.getParameter("accountID2")).thenReturn("");
	     when(request2.getParameter("transName")).thenReturn("");
	     when(request2.getParameter("amount")).thenReturn("100.50");
	     when(request2.getParameter("amount")).thenReturn("DKK");
	     when(request2.getParameter("ID")).thenReturn("1234512345");
			
//	      PrintWriter writer = new PrintWriter("somefile.txt");
//	      //when(response.getWriter()).thenReturn(writer);
//	      new Login().doPost(request, response);
//	      //new Tr
//
//	      verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
//	      writer.flush(); // it may not have been flushed yet...
//	      assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8").contains("My Expected String"));
//	        
//		try {
//			db = new Database();
//			User user = new User(db, cpr);
//			BigDecimal interest = new BigDecimal(interestInt.replaceAll(",", ""));
//			BigDecimal balance = new BigDecimal("0".replaceAll(",", ""));
//			String accountID = generateAccountID(user);
//			Account account = new Account(user, accountID, name, balance, interest, status, new LinkedList<Transaction>());
//			message = user.addAccount(account);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		
//		assertEquals(message, "Success in Creating Account");
	}
}
