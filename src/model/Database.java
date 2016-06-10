package model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import java.sql.*;

public class Database {
	private Connection connection;
	private Statement statement;
	private HttpSession session;

	private String url = "jdbc:db2://192.86.32.54:5040/DALLASB:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
	private String loginString = "Illegal action";
	private String invalidCPR = "CPR number is invalid";
	private String invalidPhone = "Phone number is invalid";

	public Database(HttpSession session) throws ClassNotFoundException, SQLException {
		connect();
		this.session = session;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		connection = DriverManager.getConnection(url, "DTU10", "FAGP2016");
		statement = connection.createStatement();
	}

	public ArrayList<User> searchFor(String keyword) {
		if (session != null && session.getAttribute("role").equals("e")) {
			ArrayList<String> IDs = new ArrayList<>();
			ArrayList<User> users = new ArrayList<>();

			try {
				String query = "SELECT * FROM \"DTUGRP05\".\"CUSTOMERS\" WHERE \"CPRNo\" LIKE ? OR \"Phone\" LIKE ? OR LOWER(\"FullName\") LIKE ?";
				PreparedStatement stmt = connection.prepareStatement(query);
				stmt.setString(1, "%"+keyword+"%");
				stmt.setString(2, "%"+keyword+"%");
				stmt.setString(3, "%"+keyword+"%");
				ResultSet resultSet = stmt.executeQuery();
				while (resultSet.next()) {
					IDs.add(resultSet.getString("CPRNo"));
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (String ID : IDs) {
				users.add(getUser(ID));
			}
			return users;
		}
		return null;
	}

	public String Login(String cpr, String password) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".Login(?, ?, ?, ?) }");
		call.setString("vCPRNo", cpr);
		call.setString("vPassword", password);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.registerOutParameter("vRoleID", java.sql.Types.CHAR);
		call.execute();
		if (call.getString("vOutput").equals("T")) {
			return call.getString("vRoleID");
		} else {
			return call.getString("vRoleID");
		}
	}

	public LinkedList<Account> getAccounts(User user) {
		String cpr = user.getCPR();
		LinkedList<Account> accounts = new LinkedList<>();
		LinkedList<User> owners = new LinkedList<>();
		owners.add(user);

		try {
			String query = "select * from \"DTUGRP05\".\"ACCOUNTS\" LEFT OUTER JOIN \"DTUGRP05\".\"OWNERSHIPS\" ON \"DTUGRP05\".\"ACCOUNTS\".\"AccID\" = \"DTUGRP05\".\"OWNERSHIPS\".\"AccID\" WHERE \"CPRNo\" = ? ";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, cpr);
			ResultSet resultset = stmt.executeQuery();
			while (resultset.next()) {
				Account acc = new Account(owners, resultset.getString("AccID"), resultset.getString("AccName"),
						resultset.getBigDecimal("Balance"), resultset.getBigDecimal("Interest"),
						resultset.getString("ISOCode"), null);
				accounts.add(acc);
			}
			resultset.close();
			for (Account acc : accounts) {
				acc.setTransactions(getTransactions(acc.getAccountID()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public LinkedList<Transaction> getTransactions(String accountID) {
		LinkedList<Transaction> transactions = new LinkedList<>();

		try {
			String query = "select * from \"DTUGRP05\".\"TRANSACTIONS\" WHERE \"AccID\" = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, accountID);
			ResultSet resultset = stmt.executeQuery();
			while (resultset.next()) {
				BigDecimal amount = resultset.getBigDecimal("Amount");
				if (resultset.getString("TransType").equals("Transaction Send Money")
						|| resultset.getString("TransType").equals("Withdraw")) {
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

	public String newAccount(Account account, User user) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			String cpr = user.getCPR();
			String ID = account.getAccountID();
			String name = account.getName();
			BigDecimal balance = account.getBalance();
			BigDecimal interest = account.getInterest();
			String ISOCode = account.getISOCode();
			
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".CreateAccount(?, ?, ?, ?, ?, ?, ?) }");
			call.setString("vCPRNo", cpr);
			call.setString("vAccID", ID);
			call.setBigDecimal("vAmount", balance);
			call.setBigDecimal("vInterest", interest);
			call.setString("vAccName", name);
			call.setString("vISOCode", ISOCode);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public String closeAccount(String accountID) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".DeleteAccount(?, ?) }");
			call.setString("vAccID", accountID);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public String editAccount(Account account) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".EditAccount(?, ?, ?, ?) }");
			call.setString("vAccID", account.getAccountID());
			call.setString("vAccName", account.getName());
			call.setBigDecimal("vInterest", account.getInterest());
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public String processTransaction(String type, String accountID, String accountID2, BigDecimal amount, String ISOCode, String transactionName) throws SQLException {
		if (session != null && session.getAttribute("loggedinuser") != null) {
			CallableStatement call;
			switch (type) {
			case "Deposit":
				call = connection.prepareCall("{call \"DTUGRP05\".Deposit(?, ?, ?, ?) }");
				call.setString("vAccID", accountID);
				call.setBigDecimal("vAmount", amount);
				call.setString("vISOCode", ISOCode);
				call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
				call.execute();
				return call.getString("vOutput");
			case "Withdraw":
				call = connection.prepareCall("{call \"DTUGRP05\".withdraw(?, ?, ?, ?) }");
				call.setString("vAccID", accountID);
				call.setBigDecimal("vAmount", amount);
				call.setString("vISOCode", ISOCode);
				call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
				call.execute();
				return call.getString("vOutput");
			case "Transfer":
				call = connection.prepareCall("{call \"DTUGRP05\".MoneyTransfer(?, ?, ?, ?, ?, ?) }");
				call.setBigDecimal("vTransfer", amount);
				call.setString("vTransName", transactionName);
				call.setString("vAccID1", accountID);
				call.setString("vAccID2", accountID2);
				call.setString("vISOCode", ISOCode);
				call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
				call.execute();
				return call.getString("vOutput");
			}
		}
		return loginString;
	}

	public LinkedList<User> getOwners(String accountID) {
		LinkedList<User> owners = new LinkedList<>();
		LinkedList<String> IDs = new LinkedList<>();
		try {
			String query = "select * from \"DTUGRP05\".\"OWNERSHIPS\" WHERE \"AccID\" = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, accountID);
			ResultSet resultset = stmt.executeQuery();
			while (resultset.next()) {
				 IDs.add(resultset.getString("CPRNo"));
			}
			for (String ID : IDs) {
				owners.add(getUser(ID));
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owners;
	}

	public String getCity(String postcode) {
		String city = null;
		try {
			String query = "select * from \"DTUGRP05\".\"CITIES\" WHERE \"Postcode\" = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, postcode);
			ResultSet resultset = stmt.executeQuery();
			if (resultset.next()) {
				city = resultset.getString("CityName");
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return city;
	}

	public Account getAccount(String accountID) {
		try {
			String query = "select * from \"DTUGRP05\".\"ACCOUNTS\" WHERE \"AccID\" = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, accountID);
			ResultSet resultset = stmt.executeQuery();
			if (resultset.next()) {
				BigDecimal balance = resultset.getBigDecimal("Balance");
				BigDecimal interest = resultset.getBigDecimal("Interest");
				String AccName = resultset.getString("AccName");
				String ISOCode = resultset.getString("ISOCode");
				resultset.close();

				Account account = new Account(getOwners(accountID), accountID, AccName, balance, interest,
						ISOCode, getTransactions(accountID));
				return account;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String register(String cpr, String email, String password, String name, String phone, String address,
			Date date, String postcode) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			if(cpr.length() != 10){
				return invalidCPR;
			}
			if(phone.length() != 8){
				return invalidPhone;
			}
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".UserRegister(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
			call.setString("vCPRNo", cpr);
			call.setString("vEmail", email);
			call.setString("vPassword", password);
			call.setString("vRoleID", "c");
			call.setString("vFullName", name);
			call.setString("vPhone", phone);
			call.setString("vAddress", address);
			call.setDate("vDateOfBirth", date);
			call.setString("vPostcode", postcode);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public User getUser(String cpr) {
		User user = null;
		try {
			String query = "select * from \"DTUGRP05\".\"USERS\" WHERE \"CPRNo\" = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, cpr);
			ResultSet resultset = stmt.executeQuery();
			if (resultset.next()) {
				user = new User(this, cpr);
				String email = resultset.getString("Email");
				String name = resultset.getString("FullName");
				String password = resultset.getString("Password");
				String phone = resultset.getString("Phone");
				String address = resultset.getString("Address");
				Date dateOfBirth = resultset.getDate("DateOfBirth");
				String postCode = resultset.getString("PostCode");
				String role = resultset.getString("RoleID");
				user.setInfo(email, password, name, phone, address, dateOfBirth, postCode, role);

				LinkedList<Account> accounts = getAccounts(user);
				user.setAccounts(accounts);
			}
			resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public String editUser(String cpr, String email, String password, String name, String address, String postcode,
			Date date, String phone) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".EditUser(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
			if(cpr.length() != 10){
				return invalidCPR;
			}
			if(phone.length() != 8){
				return invalidPhone;
			}
			call.setString("vCPRNo", cpr);
			call.setString("vEmail", email);
			call.setString("vPassword", password);
			call.setString("vFullName", name);
			call.setString("vAddress", address);
			call.setString("vPostcode", postcode);
			call.setDate("vDateOfBirth", date);
			call.setString("vPhone", phone);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public String deleteUser(String cpr) throws SQLException {
		if (session != null && session.getAttribute("role").equals("e")) {
			CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".DeleteUser(?, ?) }");
			call.setString("vCPRNo", cpr);
			call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
			call.execute();
			return call.getString("vOutput");
		}
		return loginString;
	}

	public String generateAccountID() throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".FindMaxAccID(?) }");
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		String ID = call.getString("vOutput");
		BigDecimal value = (new BigDecimal(ID)).add(new BigDecimal("1"));
		return "" + value;
	}

	public String addOwner(String accountID, String newCPR) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".AddOwner(?, ?, ?) }");
		call.setString("vCPRNo", newCPR);
		call.setString("vAccID", accountID);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}

	public String deleteOwner(String accountID, String newCPR) throws SQLException {
		CallableStatement call = connection.prepareCall("{call \"DTUGRP05\".RemoveOwner(?, ?, ?) }");
		call.setString("vCPRNo", newCPR);
		call.setString("vAccID", accountID);
		call.registerOutParameter("vOutput", java.sql.Types.VARCHAR);
		call.execute();
		return call.getString("vOutput");
	}
}
