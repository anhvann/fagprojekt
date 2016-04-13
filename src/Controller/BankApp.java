package Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import com.ibm.db2.jcc.am.Connection;



public class BankApp {
	
	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
	
	public BankApp() {
		CreateConnection();
	}
	
	private void CreateConnection() {
		try {
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Driver not found");
				e.printStackTrace();
			}
			System.out.println();
			System.out.println("Connection etablished");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			System.out.println("Connection Failed!");
			System.out.println();
		}
	}
	
	public ResultSet getAccountInfo(String email, String password) throws SQLException {
		System.out.println("THE EMAIL AND PASSWORD FROM INPUT TEST SCREEN IS");
		System.out.println(email + " " + password);
		System.out.println("************************************************************************");
		
		Connection connection = (Connection) DriverManager.getConnection(url, "DTU10", "FAGP2016");
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM DTUGRP05.USERINFO WHERE \"Email\"=? AND \"Password\"=?");
		statement.setString(1, email);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		
		statement.close();
		connection.close();
		
		return resultSet;
	}
	
	public ResultSet getDatabase() throws SQLException {
		
		Connection connection = (Connection) DriverManager.getConnection(url, "DTU10", "FAGP2016");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM DTUGRP05.USERINFO");
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return resultSet;
	}
	
	
}
