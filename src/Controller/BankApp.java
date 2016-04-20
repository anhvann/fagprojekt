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

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public String getRole(String cpr, String password) throws SQLException, ClassNotFoundException {
		connect();
		ResultSet resultSet = null;

		try {
			resultSet = statement
					.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + cpr + "' ");
			while (resultSet.next()) {
				if (resultSet.getString("Password").equals(password)) {
					return resultSet.getString("RoleID");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultSet.close();
		connection.close();
		return "";
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

	public LinkedList<String> getAccounts(String userID) throws ClassNotFoundException, SQLException {
		connect();

		LinkedList<String> accounts = new LinkedList<String>();
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(
					"SELECT \"ACCOUNTS\" FROM \"DTUGRP05\".\"OWNERSHIP\" WHERE \"CPRNo\" = '" + userID + "'");
			while (resultSet.next()) {
				accounts.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultSet.close();
		connection.close();

		return accounts;
	}
	
	public LinkedList<String> getInfo(String userID) throws ClassNotFoundException, SQLException {
		connect();
		
		LinkedList<String> list = new LinkedList<>();
		ResultSet rs = null;
		int index = 2;
		
		try {
			rs = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = '" + userID + "'");
			while (rs.next()) {
				list.add(rs.getString(index));
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.close();
		connection.close();
		
		return list;
	}
}
