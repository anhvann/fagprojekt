package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.*;

import model.Database;

public class TestLogin {
	
	Database db;
	LinkedList<String> results;
	String dbpassword, dbrole;
	String[] columns = { "FullName", "Password", "RoleID" };
	
	@Before 
	public void setUp() throws Exception {
		db = new Database();
	}
	
	@Test
	public void testNormalLogin() throws Exception {
		String cpr = "1234567891";
		String password = "anna0207";
		String role = "e";
		
		//cpr exists
		results = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
			String dbpassword = results.get(1);
		dbrole = results.get(2);
		
		assertEquals(dbpassword, password);
		assertEquals(dbrole, role);
		
	}
	
	@Test
	public void testWrongPassword() throws Exception {
		//wrong password
		String cpr = "1234567891";
		String password = "anna";
		String role = "e";
		
		results = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
		dbpassword = results.get(1);
		dbrole = results.get(2);
		
		assertFalse(dbpassword == password);
		assertEquals(dbrole, role);
	}
	
	@Test
	public void testWrongCPR() throws ClassNotFoundException, SQLException {
		//cpr doesn't exist
		String cpr = "1231231231";
		
		results = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(results, list);
	}
	
	@Test
	public void testEmptyInput() throws Exception {
		String cpr = "";
		
		results = db.getStrings("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ", columns);
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(results, list);
		
	}

}
