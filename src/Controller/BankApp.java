package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;
import java.sql.*;

public class BankApp {
	private Connection connection;
	private Statement statement;

	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";

	public String getRole(String email, String password) throws SQLException, ClassNotFoundException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"Email\" = '" + email + "' ");
			while (resultSet.next()) {
				if (resultSet.getString("Password").equals(password)) {
					return resultSet.getString("Role");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultSet.close();
		statement.close();
		connection.close();
		return "";
	}

	// public void searchFor(String input) throws SQLException {
	// connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
	// statement = connection.createStatement();
	//
	// LinkedList<ResultSet> allResults = new LinkedList<>();
	// Scanner scanner = new Scanner(input);
	//
	// try {
	// while (scanner.hasNext()) {
	// String keyword = scanner.next();
	// ResultSet resultSet = statement.executeQuery("SELECT * FROM
	// DTUGRP05.USERS WHERE LOWER(\"Name\") LIKE '%" + keyword
	// + "%' OR LOWER(\"Address\") LIKE '%" + keyword + "%' OR LOWER(\"Email\")
	// LIKE '%"
	// + keyword + "%' OR \"Phone\" LIKE '%" + keyword + "%'");
	// allResults.add(resultSet);
	// System.out.println(resultSet.getString("Name"));
	// }
	// LinkedList<String> IDs = new LinkedList<>();
	// ResultSet firstSet = allResults.get(0);
	// //Compare every result set with the first one
	// for (int i = 1; i < allResults.size(); i++) {
	// //For every ID in first set
	// while (firstSet.next()) {
	// String id = firstSet.getString("UserID");
	// ResultSet otherSet = allResults.get(i);
	// //For every ID in other sets
	// while(otherSet.next()){
	// if(otherSet.getString("UserID").equals(id)){
	// IDs.add(id);
	// System.out.print(id);
	// }
	// }
	// }
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// scanner.close();
	// }
}
