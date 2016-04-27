package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import model.Account;
import model.Transaction;
import model.User;

import java.sql.*;

public class Database {
	private Connection connection;
	private Statement statement;

	protected String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";


	public Database() throws ClassNotFoundException, SQLException{
		connect();
	}
	
	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public LinkedList<String> searchFor(String input){
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
			resultSet.close();
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
				otherResultSet.close();
				IDs = (LinkedList<String>) IDs2.clone();
				IDs2.clear();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return IDs;
	}

	public LinkedList<String> getStrings(String query, String[] columns) {
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

	public LinkedList<Account> getAccounts(User user) {
		String cpr = user.getCPR();
		LinkedList<Account> accounts = new LinkedList<>();

		try {
			ResultSet resultset = statement.executeQuery("select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = '"  + cpr + "' ");
			while (resultset.next()) {
				Account acc = new Account(user, resultset.getString("AccID"), resultset.getString("AccName"), resultset.getBigDecimal("Balance"), resultset.getBigDecimal("Interest"), resultset.getString("Status"));
				accounts.add(acc);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public void newAccount(Account account) throws SQLException {
		String cpr = account.getOwner().getCPR();
		String ID = account.getAccountID();
		String name = account.getName();
		BigDecimal balance = account.getBalance();
		BigDecimal interest = account.getInterest();
		String status = account.getStatus();
		
		statement.executeUpdate("CALL \"DTUGRP05\".CreateAccount(" + cpr + ", " + ID + ", " + balance + ", " + interest + ", " + name + ", " + status + ")");
	}

	public void closeAccount(String accountID) throws SQLException {
		statement.executeUpdate("CALL \"DTUGRP05\".\"CloseDownAccount\"('"+accountID+"')");
	}

	public void processTransaction(String type, String accountID, BigDecimal amount, String currency) throws SQLException {
		if(type.equals("Deposit")){
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".deposit(?, ?, ?) }");
			call.setString("vAccID", accountID);
			call.setBigDecimal("vAmount", amount);
			call.setString("vISOCode", currency);
			call.execute();
		} else if (type.equals("Withdraw")){
			
		} else if(type.equals("Transfer")){
			
		}
	}
}
