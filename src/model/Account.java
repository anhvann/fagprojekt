package model;

import java.util.LinkedList;

public class Account {

	private String accountID;
	private double balance;
	
	public Account(String accountID, double balance) {
		this.accountID = accountID;
		this.balance = balance;
	}
	public double getBalance(){
		return balance;
	}
	
	public String getAccountID(){
		return accountID;
	}
}