package model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Account {

	private String accountID, status, name;
	private User user;
	private BigDecimal interest, balance;
	private LinkedList<Transaction> transactions;
	
	public Account(User user, String accountID, String name, BigDecimal balance, BigDecimal interest, String status) {
		this.user = user;
		this.accountID = accountID;
		this.name = name;
		this.interest = interest;
		this.status = status;
		this.transactions = new LinkedList<>();
		this.balance = balance;
	}
	
	public BigDecimal getBalance(){
		return balance;
	}
	
	public String getAccountID(){
		return accountID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}
	
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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