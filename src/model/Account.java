package model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

public class Account {

	private String accountID, status, name;
	private User user;
	private BigDecimal balance, interest;
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
	
	public String getBalanceString(){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("###,##0.00", symbols);
		return formatter.format(balance.longValue());
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
}