package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import model.Database;

public class User {

	private String cpr, email, password, name, phone, address, postCode, roleID;
	private Date dateOfBirth;
	private ArrayList<Account> accounts;
	private Database bank;

	public User(Database database, String cpr) {
		this.bank = database;
		this.cpr = cpr;
		this.accounts = new ArrayList<>();
	}

	public void setInfo(String email, String password, String name, String phone, String address, Date dateOfBirth, String postCode, String roleID) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.postCode = postCode;
		this.roleID = roleID;
	}

	public ArrayList<Account> getAccounts() {
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	public String editAccount(Account account) throws ClassNotFoundException, SQLException {
		return bank.editAccount(account);
	}

	public String addAccount(Account account) throws ClassNotFoundException, SQLException {
		accounts.add(account);
		return bank.newAccount(account, this);
	}

	public String getCPR() {
		return cpr;
	}

	public String closeAccount(String accID) throws SQLException {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountID().equals(accID)) {
				accounts.remove(i);
				break;
			}
		}
		return bank.closeAccount(accID);
	}

	public Account getAccount(String accID) {
		for (Account account : accounts) {
			if (account.getAccountID().equals(accID)) {
				return account;
			}
		}
		return null;
	}

	public BigDecimal getBalance(String accountID) {
		for (Account acc : accounts){
			if (acc.getAccountID().equals(accountID)){
				return acc.getBalance();
			}
		}
		return new BigDecimal(0);
	}
}