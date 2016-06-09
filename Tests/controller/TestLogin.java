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
	 * Precondition: Employee CPR: 1234567891, password: anna0207 Client CPR:
	 * 2309911234, password: daisy2 Non-existent CPR: 1584145477
	 */

	@Test
	public void testLoginEmployee() throws Exception {
		String cpr = "1234567891";
		String password = "anna0207";
		Login login = new Login();
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		assertEquals(cpr, login.getLoggedInUser());
		assertEquals("e", login.getRole());
	}

	@Test
	public void testLoginClient() throws Exception {
		String cpr = "2309911234";
		String password = "daisy2";
		Login login = new Login();
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		assertEquals(cpr, login.getLoggedInUser());
		assertEquals("c", login.getRole());
	}

	@Test
	public void testWrongPassword() throws Exception {
		String cpr = "9876543219";
		String password = "daisy2";
		Login login = new Login();
		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		assertNull(login.getLoggedInUser());
		assertNull(login.getRole());
	}

	@Test
	public void testNonexistentUser() throws Exception {
		String cpr = "1584145477";
		String password = "daisy2";
		Login login = new Login();

		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("cpr")).thenReturn(cpr);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		login.doPost(request, response);
		assertNull(login.getLoggedInUser());
		assertNull(login.getRole());
	}
}
