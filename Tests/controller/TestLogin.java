package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;

public class TestLogin {
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);

	/*
	 * Precondition: 
	 * 		Employee CPR: 1234567891
	 * 		Password: anna0207 
	 * 	Client 
	 * 		CPR: 2309911234
	 * 		Password: daisy2 
	 * 	Non-existent CPR: 1584145477
	 */
	@Before
	public void initialization(){
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	}
	
	/*An employee successfully logs in
	 * Getting the logged in user should return the CPR number "1234567891"
	 * The role of the logged in user should be "e"*/
	@Test
	public void testLoginEmployee() throws Exception {
		String cpr = "1234567891";
		String password = "anna0207";
		Login login = new Login();
		
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		
		login.doPost(request, response);
		assertEquals(cpr, login.getLoggedInUser());
		assertEquals("e", login.getRole());
	}

	/*A client successfully logs in
	 * Getting the logged in user should return the CPR number "230991234"
	 * The role of the logged in user should be "c"*/
	@Test
	public void testLoginClient() throws Exception {
		String cpr = "2309911234";
		String password = "daisy2";
		Login login = new Login();
		
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);

		login.doPost(request, response);
		assertEquals(cpr, login.getLoggedInUser());
		assertEquals("c", login.getRole());
	}

	/*The password does not match the CPR number so no user is logged in
	 * Getting the logged in user should return null
	 * The role of the logged in user should be null
	 * The message returned by the database should be "CPR number and password did not match"*/
	@Test
	public void testWrongPassword() throws Exception {
		String cpr = "9876543219";
		String password = "daisy2";
		Login login = new Login();
		
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);

		login.doPost(request, response);
		assertEquals("CPR number and password did not match", login.getMessage());
		assertNull(login.getLoggedInUser());
		assertNull(login.getRole());
	}

	/*The ID is not registered in the database so no user should be logged in
	 * Getting the logged in user should return null
	 * The role of the logged in user should be null*/
	@Test
	public void testNonexistentUser() throws Exception {
		String cpr = "1584145477";
		String password = "daisy2";
		Login login = new Login();

		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);

		login.doPost(request, response);
		assertNull(login.getLoggedInUser());
		assertNull(login.getRole());
	}
	
	/*The ID is of a length not accepted by the login procedure: 23099112345 
	 * Getting the logged in user should return null
	 * The role of the logged in user should be null
	 * The message returned should be "Given CPR number is invalid"*/
	@Test
	public void testInvalidCPR() throws Exception {
		String cpr = "23099112345";
		String password = "daisy2";
		Login login = new Login();

		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);

		login.doPost(request, response);
		assertEquals("Given CPR number is invalid", login.getMessage());
		assertNull(login.getLoggedInUser());
		assertNull(login.getRole());
	}
}