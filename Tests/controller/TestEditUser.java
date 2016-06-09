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
	private String zipcode = "2800";
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
	 * 		Zipcode: 2800
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
	    when(request.getParameter("zipcode")).thenReturn(zipcode);
	    when(request.getParameter("date")).thenReturn(date);
	    when(request.getParameter("phone")).thenReturn(phone);
		userActivity.doPost(request, response);
		
	}
	@Test
	public void testEditUserSuccess() throws Exception {
		String email = "voldemort@gmail.com";
		String name = "Voldemort";
		
		User user = db.getUser(clientCpr);
		assertEquals("tomriddle@gmail.com", user.getEmail());
		assertEquals("Tom Marvolo Riddle", user.getName());
		
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

		user = db.getUser(clientCpr); //get updated user
		assertEquals(email, user.getEmail());
		assertEquals(name, user.getName());
		assertEquals("User successfully edited", userActivity.getMessage());
	}
	
	@Test
	public void testEditInvalidZip() throws Exception {
		String newzipcode = "0000";
		
	    when(request.getParameter("zipcode")).thenReturn(newzipcode);
		userActivity.doPost(request, response);
		
		assertEquals(zipcode, db.getUser(clientCpr).getPostCode());
		assertEquals("Zipcode not found", userActivity.getMessage()); //Wrong message
	}
	
	//View pages
	@Test
	public void testViewEditPage() throws Exception {
		String action = "edit";
		String email = "voldemort@gmail.com";
		String name = "Voldemort";
		
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
	@Test
	public void testViewUser() throws Exception {
		String action = "viewuser";
		String email = "voldemort@gmail.com";
		String name = "Voldemort";
		
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
