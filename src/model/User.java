package model;

import java.sql.SQLException;
import java.util.LinkedList;

import controller.Database;

public class User {

	private String cpr, email, password, name, phone, address, dateOfBirth, postCode, roleID;
	private LinkedList<Account> accounts;
	private Database bank;

	public User(Database bank, String cpr) {
		this.bank = bank;
		this.cpr = cpr;
		this.accounts = new LinkedList<>();
	}

	public void setInfo(LinkedList<String> info) {
		email = info.get(1);
		password = info.get(2);
		name = info.get(3);
		phone = info.get(4);
		address = info.get(5);
		dateOfBirth = info.get(6);
		postCode = info.get(7);
		roleID = info.get(8);
	}

	public LinkedList<Account> getAccounts() {
		return accounts;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setAccounts(LinkedList<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) throws ClassNotFoundException, SQLException {
		accounts.add(account);
		bank.newAccount(account);
	}

	public String getCPR() {
		return cpr;
	}

	public void closeAccount(String accID) throws SQLException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountID().equals(accID)) {
				if (accounts.get(i).getBalance() == 0) {
					accounts.remove(i);
					bank.closeAccount(accID);
					break;
				} else {
					break;
				}
			}
		}
	}

	public Account getAccount(String accID) {
		for (Account account : accounts) {
			if (account.getAccountID().equals(accID)) {
				return account;
			}
		}
		return null;
	}

	public LinkedList<Account> getTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
}