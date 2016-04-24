package model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Account {

	private String accountID, status;
	private double balance;
	private User user;
	private BigDecimal interest;
	private LinkedList<Transaction> transactions;
	
	public Account(User user, String accountID, double balance, BigDecimal interest, String status) {
		this.user = user;
		this.accountID = accountID;
		this.interest = interest;
		this.status = status;
		this.transactions = new LinkedList<>();
		this.balance = balance;
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
	
	public LinkedList<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(LinkedList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Transaction getTransaction(String transID) {
		for (Transaction transaction : transactions) {
			if (transaction.getTransID().equals(transID)) {
				return transaction;
			}
		}
		return null;
	}
}