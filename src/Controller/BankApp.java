package Controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import model.Account;
import model.User;

import java.sql.*;

public class BankApp {
	private Connection connection;
	private Statement statement;

	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public LinkedList<String> searchFor(String input) throws SQLException, ClassNotFoundException {
		connect();
		ResultSet resultSet = null;
		Scanner scanner = new Scanner(input);
		LinkedList<String> IDs = new LinkedList<>();
		LinkedList<String> IDs2 = new LinkedList<>();

		// Get first set
		String keyword = scanner.next();
		try {
			resultSet = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" LIKE '%" + keyword
					+ "%' OR LOWER(\"Email\") LIKE '%" + keyword + "%' OR LOWER(\"FullName\") LIKE '%" + keyword
					+ "%' OR \"Phone\" LIKE '%" + keyword + "%' OR LOWER(\"Address\") LIKE '%" + keyword + "%'"
					+ "OR \"Postcode\" LIKE '%" + keyword + "%'");
			while (resultSet.next()) {
				IDs.add(resultSet.getString("CPRNo"));
			}
			// Compare first set with other
			while (scanner.hasNext()) {
				keyword = scanner.next();
				ResultSet otherResultSet = statement
						.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" LIKE '%" + keyword
								+ "%' OR LOWER(\"Email\") LIKE '%" + keyword + "%' OR LOWER(\"FullName\") LIKE '%"
								+ keyword + "%' OR \"Phone\" LIKE '%" + keyword + "%' OR LOWER(\"Address\") LIKE '%"
								+ keyword + "%'" + " OR \"Postcode\" LIKE '%" + keyword + "%'");

				while (otherResultSet.next()) {
					for (int i = 0; i < IDs.size(); i++) {
						if (IDs.get(i).equals(otherResultSet.getString("CPRNo"))) {
							IDs2.add(IDs.get(i));
							break;
						}
					}
				}
				IDs = (LinkedList<String>) IDs2.clone();
				IDs2.clear();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return IDs;
	}

//	public LinkedList<String> getInfo(String userID) throws ClassNotFoundException, SQLException {
//		connect();
//
//		LinkedList<String> list = new LinkedList<>();
//		ResultSet rs = null;
//		int index = 2;
//
//		try {
//			rs = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + userID + "'");
//			while (rs.next()) {
//				list.add(rs.getString(index));
//				index++;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		rs.close();
//		connection.close();
//
//		return list;
//	}

	public LinkedList<String> queryExecution(String query, String[] columns)
			throws ClassNotFoundException, SQLException {
		connect();

		LinkedList<String> results = new LinkedList<>();

		try {
			ResultSet resultset = statement.executeQuery(query);
			while (resultset.next()) {
				for (int i = 0; i < columns.length; i++) {
					results.add(resultset.getString(columns[i]));
				}
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	public LinkedList<Account> getAccounts(String cpr) throws ClassNotFoundException, SQLException {
		connect();
		
		LinkedList<Account> accounts = new LinkedList<>();

		try {
			ResultSet resultset = statement.executeQuery("select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = '"  + cpr + "' ");
			while (resultset.next()) {
				Account acc = new Account(resultset.getString("AccID"), resultset.getDouble("Balance"));
				accounts.add(acc);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}
}
