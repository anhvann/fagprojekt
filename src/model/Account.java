package model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Account {

	private String accountID, status;
	private double balance;
	private User user;
	private BigDecimal interest;
	
	public Account(User user, String accountID, double balance, BigDecimal interest, String status) {
		this.user = user;
		this.accountID = accountID;
		this.balance = balance;
		this.interest = interest;
		this.status = status;
	}
	public double getBalance(){
		return balance;
	}
	public String getAccountID(){
		return accountID;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public String getStatus() {
		return status;
	}
	public User getOwner() {
		return user;
	}
}