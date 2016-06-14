package controller;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;
import model.Database;
import model.User;

public class TestRegisterDeleteUser {
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
	private String postcode = "2800";
	private String date = "1989-08-02";
	private String phone = "63573311";
	
	/*Precondition:
	 * Employee:
	 * 		CPR: 9876543219	
	 * 		Password: vanvan
	 * 
	 * User with CPR 0208891133 does not exist
	 * User with CPR 2309911234 already exists
	 * User with CPR 3112261111 still has accounts
	 * User with CPR 1805935555 has no accounts
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
		//Register
		String action = "register";
		assertNull(db.getUser(clientCpr)); //ensure user does not already exists
		
		callServlet(action, clientCpr, postcode, phone);
		User user = db.getUser(clientCpr);
		assertEquals("User registered successfully",userActivity.getMessage());
		assertNotNull(user);
		
		//Delete
		action = "delete";
		when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    userActivity.doPost(request, response);
	    
	    assertEquals("User deleted", userActivity.getMessage());
	    assertNull(db.getUser(clientCpr));
	}
	
	@Test
	public void testRegisterUserWithUsedCPR() throws Exception {
		//Register
		String action = "register";
		callServlet(action, "2309911234", postcode, phone);
		
		assertEquals("User is already registered", userActivity.getMessage());
	}
	
	@Test
	public void testRegisterUserInvalidPostcode() throws Exception {
		//Register
		String action = "register";
		callServlet(action, "1504902584", "0000", phone);

		assertEquals("Invalid Postal Code", userActivity.getMessage());
		assertNull(db.getUser(clientCpr));

	}
	
	@Test
	public void testDeleteUserWithAccounts() throws Exception {
		String action = "delete";
		callServlet(action, "3112261111", postcode, phone);
		
		assertEquals("User still has account(s)",userActivity.getMessage());
	}
	
	@Test
	public void testRegisterInvalidPostCode() throws Exception {
		String action = "register";
		callServlet(action, clientCpr, "280", phone);
		
		assertEquals("Invalid postal code", userActivity.getMessage());
	}
	
	@Test
	public void testRegisterInvalidPhone() throws Exception {
		String action = "register";
		callServlet(action, clientCpr, postcode, "635733111");
		
		assertEquals("Invalid phone number", userActivity.getMessage());
	}
	
	@Test
	public void testRegisterInvalidCPR() throws Exception {
		String action = "register";
		callServlet(action, "02088911333", postcode, phone);
		
		assertEquals("Invalid CPR number", userActivity.getMessage());
	}
	
	private void callServlet(String action, String clientCpr, String postcode, String phone) throws ServletException, IOException {
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
	
	//Not possible through user interface
	@Test
	public void testRegisterNotLoggedIn() throws Exception {
		when(request.getSession()).thenReturn(null);
		String action = "register";
		assertNull(db.getUser(clientCpr)); //ensure user does not already exists
		
		callServlet(action, clientCpr, postcode, phone);
		User user = db.getUser(clientCpr);
		assertEquals("Illegal action",userActivity.getMessage());
		assertNull(user);
	}
	@Test
	public void testRegisterAsClient() throws Exception {
		when(request.getSession().getAttribute("role")).thenReturn("c");
		String action = "register";
		assertNull(db.getUser(clientCpr)); //ensure user does not already exists
		
		callServlet(action, clientCpr, postcode, phone);
		User user = db.getUser(clientCpr);
		assertNull(user);
		assertEquals("Illegal action",userActivity.getMessage());
	}
	@Test
	public void testDeleteNotLoggedIn() throws Exception {
		when(request.getSession()).thenReturn(null);
		String action = "delete";
		String clientCpr = "1805935555";
		when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    userActivity.doPost(request, response);
	    
	    assertEquals("Illegal action", userActivity.getMessage());
	    assertNotNull(db.getUser(clientCpr));
	}
	@Test
	public void testDeleteAsClient() throws Exception {
		when(request.getSession().getAttribute("role")).thenReturn("c");
		String action = "delete";
		String clientCpr = "1805935555";
		when(request.getParameter("ID")).thenReturn(clientCpr);
	    when(request.getParameter("action")).thenReturn(action);
	    userActivity.doPost(request, response);
	    
	    assertEquals("Illegal action", userActivity.getMessage());
	    assertNotNull(db.getUser(clientCpr));
	}
}
