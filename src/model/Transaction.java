package model;
import java.sql.Date;

public class Transaction {

	private String accountID, transID, name;
	private double amount;
	private Date date;
	
	public Transaction(String transID, String name, String accountID, double amount, Date date) {
		this.transID = transID;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.accountID = accountID;
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
}
