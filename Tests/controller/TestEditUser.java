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

import model.Account;
import model.Database;
import model.Transaction;
import model.User;

public class TestEditUser {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private UserActivity userActivity;
	private Database db;
	private String clientCpr = "3112261111";
	private String action = "change";
	private String email = "tomriddle@gmail.com";
	private String clientPassword = "slytherin4ever";
	private String name = "Tom Marvolo Riddle";
	private String address = "Anker Engelunds Vej 2";
	private String postcode = "2800";
	private String date = "31-12-1926";
	private String phone = "02059811";
	
	/*Precondition:
	 * Employee:
	 * 		CPR: 9876543219	
	 * 		Password: vanvan
	 * Initial user: 3112261111
	 * 		E-mail address: tomriddle@gmail.com
	 * 		Password: slytherin4ever
	 * 		Name: Tom Marvolo Riddle
	 * 		Address: Anker Engelunds Vej 2 
	 * 		postcode: 2800
	 * 		Date of Birth: 31-12-1926
	 * 		Phone: 02059811
	*/
	@Before
	public void loginAndReset() throws ServletException, IOException, ClassNotFoundException, SQLException{
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
	    userActivity = new UserActivity();
		
	    db = new Database(session);
		
		//Reset account to initial values
	    when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    when(request.getParameter("email")).thenReturn(email);
	    when(request.getParameter("password")).thenReturn(clientPassword);
	    when(request.getParameter("name")).thenReturn(name);
	    when(request.getParameter("address")).thenReturn(address);
	    when(request.getParameter("postcode")).thenReturn(postcode);
	    when(request.getParameter("date")).thenReturn(date);
	    when(request.getParameter("phone")).thenReturn(phone);
		userActivity.doPost(request, response);
		
	}
	/*A logged in employee successfully edits a user
	 * The user's new e-mail address should be "voldemort@gmail.com"
	 * The user's new name should be "Voldemort" 
	 * The returned message from the database should be "User Edit completed"*/
	@Test
	public void testEditUserSuccess() throws Exception {
		String email = "voldemort@gmail.com";
		String name = "Voldemort";
		
		User user = db.getUser(clientCpr);
	    when(request.getParameter("email")).thenReturn(email);
	    when(request.getParameter("name")).thenReturn(name);
		userActivity.doPost(request, response);

		user = db.getUser(clientCpr); //get updated user
		assertEquals("User Edit completed", userActivity.getMessage());
		assertEquals(email, user.getEmail());
		assertEquals(name, user.getName());
		
	}
	
	/*The postal code is changed to an unregistered postal code: 0000
	 * The user's postal code after the attempt is the same as before: 2800 
	 * The returned message from the database should be "Invalid Postal Code"*/
	@Test
	public void testEditInvalidPostCode() throws Exception {
		String newpostcode = "0000";
		
	    when(request.getParameter("postcode")).thenReturn(newpostcode);
		userActivity.doPost(request, response);
		
		assertEquals(postcode, db.getUser(clientCpr).getPostCode());
		assertEquals("Invalid Postal Code", userActivity.getMessage());
	}

	/*The postal code is changed to a postal code not accepted by the edit procedure because of its length: 28000
	 * The user's postal code after the attempt is the same as before: 2800 
	 * The returned message should be "Invalid Postal Code"*/
	@Test
	public void testEditInvalidPostCode2() throws Exception {
		String newPostCode = "28000"; //five digits
		
	    when(request.getParameter("postcode")).thenReturn(newPostCode);
		userActivity.doPost(request, response);
		
		assertEquals(postcode, db.getUser(clientCpr).getPostCode());
		assertEquals("Invalid postal code", userActivity.getMessage());
	}
	
	/*The phone number is changed to a phone number not accepted by the edit procedure because of its length: 125488693
	 * The user's phone number after the attempt is the same as before: 02059811   
	 * The returned message should be "Invalid phone number"*/
	@Test
	public void testInvalidPhoneNumber() throws Exception {
		String newpPhoneNumber = "125488693";
 		
	    when(request.getParameter("phone")).thenReturn(newpPhoneNumber);
		userActivity.doPost(request, response);
		
		assertEquals(phone, db.getUser(clientCpr).getPhone());
		assertEquals("Invalid phone number", userActivity.getMessage());
	}
	
	//The following scenarios cannot occur with the current interface but has been covered for security reasons
	
	/*An employee not logged in cannot edit a user
	 * The user's e-mail address after the attempt is the same as before: "tomriddle@gmail.com"
	 * The user's name after the attempt is the same as before: "Tom Marvolo Riddle"   
	 * The returned message should be "Illegal action"*/
	@Test
	public void testNotLoggedIn() throws Exception {
		when(request.getSession().getAttribute("loggedinuser")).thenReturn(null);
		String newemail = "voldemort@gmail.com";
		String newname = "Voldemort";
		
		User user = db.getUser(clientCpr);
		assertEquals("tomriddle@gmail.com", user.getEmail());
		assertEquals("Tom Marvolo Riddle", user.getName());
		
	    when(request.getParameter("email")).thenReturn(newemail);
	    when(request.getParameter("name")).thenReturn(newname);
		userActivity.doPost(request, response);

		user = db.getUser(clientCpr);
		assertEquals("Illegal action", userActivity.getMessage());
		assertEquals(email, user.getEmail());
		assertEquals(name, user.getName());
	}
	
	/*A client cannot edit a user
	 * The user's e-mail address after the attempt is the same as before: "tomriddle@gmail.com"
	 * The user's name after the attempt is the same as before: "Tom Marvolo Riddle"   
	 * The returned message should be "Illegal action"*/
	@Test
	public void testEditAsClient() throws Exception {
		when(request.getSession().getAttribute("role")).thenReturn("c");
		String newemail = "voldemort@gmail.com";
		String newname = "Voldemort";
		
		User user = db.getUser(clientCpr);
		assertEquals("tomriddle@gmail.com", user.getEmail());
		assertEquals("Tom Marvolo Riddle", user.getName());
		
	    when(request.getParameter("email")).thenReturn(newemail);
	    when(request.getParameter("name")).thenReturn(newname);
		userActivity.doPost(request, response);

		user = db.getUser(clientCpr);
		assertEquals("Illegal action", userActivity.getMessage());
		assertEquals(email, user.getEmail());
		assertEquals(name, user.getName());
	}
}
