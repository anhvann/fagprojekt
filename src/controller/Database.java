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

	public Database() throws ClassNotFoundException, SQLException {
		connect();
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public LinkedList<String> searchFor(String input) {
		ResultSet resultSet = null;
		Scanner scanner = new Scanner(input);
		LinkedList<String> IDs = new LinkedList<>();
		LinkedList<String> IDs2 = new LinkedList<>();

		// Get first set
		String keyword = scanner.next();
		try {
			resultSet = statement.executeQuery("SELECT * FROM \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" LIKE '%" + keyword
					+ "%' OR LOWER(\"Email\") LIKE '%" + keyword + "%' OR LOWER(\"FullName\") LIKE '%" + keyword
					+ "%'");
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
								+ keyword + "%'");

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
			ResultSet resultset = statement.executeQuery(
					"select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = '"
							+ cpr + "' ");
			while (resultset.next()) {
				Account acc = new Account(user, resultset.getString("AccID"), resultset.getString("AccName"),
						resultset.getBigDecimal("Balance"), resultset.getBigDecimal("Interest"),
						resultset.getString("Status"));
				accounts.add(acc);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public LinkedList<Transaction> getTransactions(String accountID) {
		LinkedList<Transaction> transactions = new LinkedList<>();

		try {
			ResultSet resultset = statement
					.executeQuery("select * from \"DTUGRP05\".\"TRANSACTIONS\" WHERE \"AccID\" = '" + accountID
							+ "' OR \"AccIDTracing\" = '" + accountID + "' ");
			while (resultset.next()) {
				BigDecimal amount = resultset.getBigDecimal("Amount");
				if (resultset.getString("AccID").equals(accountID) && !resultset.getString("AccIDTracing").equals(accountID)
						|| resultset.getString("TransName").equals("Withdraw")) {
					amount = amount.negate();
				}
				Transaction trans = new Transaction(resultset.getString("TransName"), resultset.getDate("TransDate"),
						amount, resultset.getString("ISOCode"), resultset.getString("AccID"),
						resultset.getString("AccIDTracing"), resultset.getBigDecimal("ResultBalance"));
				transactions.add(trans);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactions;
	}

	public String newAccount(Account account) throws SQLException {
		String cpr = account.getOwner().getCPR();
		String ID = account.getAccountID();
		String name = account.getName();
		BigDecimal balance = account.getBalance();
		BigDecimal interest = account.getInterest();
		String status = account.getStatus();

		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".CreateAccount(?, ?, ?, ?, ?, ?, ?) }");
		call.setString("vCPRNo", cpr);
		call.setString("vAccID", ID);
		call.setBigDecimal("vAmount", balance);
		call.setBigDecimal("vInterest", interest);
		call.setString("vAccName", name);
		call.setString("vStatus", status);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String closeAccount(String accountID) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".DeleteAccount(?, ?) }");
		call.setString("vAccID", accountID);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String editAccount(Account account) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".EditAccount(?, ?, ?, ?, ?) }");
		call.setString("vAccID", account.getAccountID());
		call.setString("vAccName", account.getName());
		call.setBigDecimal("vInterest", account.getInterest());
		call.setString("vStatus", account.getStatus());
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String processTransaction(String type, String accountID, String accountID2, BigDecimal amount,
			String currency, String transactionName) throws SQLException {
		CallableStatement call;
		switch (type) {
		case "Deposit":
			call = connection.prepareCall("{call \"DTUGRP05\".deposit(?, ?, ?, ?) }");
			call.setString("vAccID", accountID);
			call.setBigDecimal("vAmount", amount);
			call.setString("vISOCode", currency);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		case "Withdraw":
			call = connection.prepareCall("{call \"DTUGRP05\".withdraw(?, ?, ?, ?) }");
			call.setString("vAccID", accountID);
			call.setBigDecimal("vAmount", amount);
			call.setString("vISOCode", currency);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		case "Transfer":
			call = connection.prepareCall("{call \"DTUGRP05\".MoneyTransfer(?, ?, ?, ?, ?, ?, ?) }");
			System.out.println(amount + " " + transactionName + " " + accountID + " " + accountID2 + " " + currency);
			call.setBigDecimal("vTransfer", amount);
			call.setString("vTransName", transactionName);
			call.setString("vAccID1", accountID);
			call.setString("vAccID2", accountID2);
			call.setString("vCurrency", currency);
			call.setString("vStatus", "?");
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return "";
	}

	public String getOwner(String accountID) {
		String cpr = "";
		try {
			ResultSet resultset = statement
					.executeQuery("select * from \"DTUGRP05\".\"OWNERSHIPS\" WHERE \"AccID\" = '" + accountID + "'");
			if (resultset.next()) {
				cpr = resultset.getString("CPRNo");
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cpr;
	}
}
