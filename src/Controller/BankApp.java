package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;
import java.sql.*;

public class BankApp {
	private Connection connection;
	private Statement statement;

	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
	// public ArrayList<String> getInfo(String password) throws SQLException {
	// // Establish the connection to DB2
	// connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
	// statement = connection.createStatement();
	//
	// // Create the result set: receive and save all information from the
	// // database
	// ResultSet resultSet = null;
	// int numCols;
	//
	// // This list is created for return statement
	// ArrayList<String> infoList = new ArrayList<String>();
	//
	// try {
	// resultSet = statement
	// .executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"Email\" = '"
	// + password + "' ");
	//
	// // ResultSetMetaData is for counting the number of column on the
	// // table
	// ResultSetMetaData rsm = resultSet.getMetaData();
	// numCols = rsm.getColumnCount();
	//
	// // // Retrieve the names of the columns by the method:
	// // getColumnName()
	// // ArrayList<String> metaDataList = new ArrayList<String>();
	// // for (int i = 1; i <= numCols; i++) {
	// // metaDataList.add(rsm.getColumnName(i));
	// // }
	// //
	// // // This loop is for doing something with the metaDataList:
	// // for (String data : metaDataList) {
	// // System.out.print(data);
	// // }
	//
	// // Retrieve all informations from the resultSet
	// while (resultSet.next()) {
	// for (int k = 1; k <= numCols; k++) {
	// infoList.add(resultSet.getString(k));
	// }
	// }
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// // Clear up enviroment
	// resultSet.close();
	// statement.close();
	// connection.close();
	//
	// return infoList;
	// }

	public String getRole(String email, String password) throws SQLException {
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
		ResultSet resultSet = null;

		try {
			resultSet = statement
					.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"Email\" = '" + email + "' ");
			while (resultSet.next()) { // if several people with same e-mail?
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
		return null;
	}

	public void searchFor(String input) throws SQLException {
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
		
		LinkedList<ResultSet> allResults = new LinkedList<>();
		LinkedList<String> keywords = new LinkedList<>();
		Scanner scanner = new Scanner(input);

		try {
			while(scanner.hasNext()){
				String keyword = scanner.next();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM DTUGRP05.USERS WHERE LOWER(\"UserName\") LIKE '%" + keyword
						+ "%' OR LOWER(\"Address\") LIKE '%" + keyword + "%' OR LOWER(\"Email\") LIKE '%" + keyword
						+ "%' OR \"Phone\" LIKE '%" + keyword + "%'");
				allResults.add(resultSet);
				
			}
			//Compare result sets
			for(int i = 1; i<allResults.size(); i++){
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
