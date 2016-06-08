package controller;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;

import model.Account;
import model.Database;
import model.Transaction;
import model.User;

public class TestRegisterAndDeleteUser {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private UserActivity userActivity;
	private Database db;
	private String clientCpr = "0208891133";
	private String email = "nm-ark@gmail.com";
	private String clientPassword = "seven";
	private String name = "Tommy Hilfiger";
	private String address = "Jernbanepladsen 65";
	private String zipcode = "2800";
	private String date = "1989-08-02";
	private String phone = "63573311";
	
	/*Precondition:
	 * Employee:
	 * 		CPR: 9876543219	
	 * 		Password: vanvan
	 * 
	 * User with CPR 0208891133 does not exist
	 * User with CPR 2309911234 already exists
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
	    userActivity = new UserActivity();
		
		db = new Database(session);
	}
	@Test
	public void testRegisterAndDeleteSuccess() throws Exception {
		//Date conversion
		Date dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		java.sql.Date dateSQL = new java.sql.Date(dateObject.getTime());
		
		//Register
		String action = "register";
		assertNull(db.getUser(clientCpr)); //ensure user does not already exist
		
		callServlet(action);
		
		User user = db.getUser(clientCpr);
		assertNotNull(user);
		assertEquals(clientCpr, user.getCPR());
		assertEquals(email, user.getEmail());
		assertEquals(clientPassword, user.getPassword());
		assertEquals(name, user.getName());
		assertEquals(address, user.getAddress());
		assertEquals(zipcode, user.getPostCode());
		assertEquals(dateSQL, user.getDateOfBirth());
		assertEquals(phone, user.getPhone());
		assertEquals("User registered successfully",userActivity.getMessage());
		
		//Delete
		action = "delete";
		when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    userActivity.doPost(request, response);
	    
	    assertEquals("User Deleted", userActivity.getMessage());
	    assertNull(db.getUser(clientCpr));
	}
	@Test
	public void testRegisterUserWithUsedCPR() throws Exception {
		//Register
		String action = "register";
		clientCpr = "2309911234";
		
		callServlet(action);
		
		assertEquals("User existed", userActivity.getMessage());
	}
	
	@Test
	public void testDeleteNonExistentUser() throws Exception {
		//Register
		String action = "delete";
		clientCpr = "0208891133";
		
		callServlet(action);
		
		assertEquals("Invalid User CPRNo",userActivity.getMessage());
	}
	
	private void callServlet(String action) throws ServletException, IOException {
		when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("email")).thenReturn(email);
	    when(request.getParameter("password")).thenReturn(clientPassword);
	    when(request.getParameter("name")).thenReturn(name);
	    when(request.getParameter("address")).thenReturn(address);
	    when(request.getParameter("zipcode")).thenReturn(zipcode);
	    when(request.getParameter("date")).thenReturn(date);
	    when(request.getParameter("phone")).thenReturn(phone);
		userActivity.doPost(request, response);
	}
}
