package model;
import java.sql.Date;

public class Transaction {

	private String accountID, accountID2, transID, name;
	private double amount;
	private Date date;
	
	//Deposit
	public Transaction(String transID, String name, String accountID, double amount, Date date) {
		this.transID = transID;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.accountID = accountID;
	}
	//Transfer
	public Transaction(String transID, String name, String accountID, String accountID2, double amount, Date date) {
		this.transID = transID;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.accountID = accountID;
		this.accountID2 = accountID2;
	}
	
	public String getTransID() {
		return transID;
	}
	
	public String getName() {
		return name;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getAccountID(){
		return accountID;
	}
	
	public String getAccountID2() {
		return accountID2;
	}
}
