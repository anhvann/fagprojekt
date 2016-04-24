package model;

import java.sql.Date;

public class Transaction {

	private String transID, name, otherAccountID;
	private double ammount;
	private Date date;
	
	public Transaction(String transID, String name, double ammount, String otherAccountID, Date date) {
		this.transID = transID;
		this.name = name;
		this.ammount = ammount;
		this.otherAccountID = otherAccountID;
		this.date = date;
	}
	
	public String getTransID() {
		return transID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOtherAccountID() {
		return otherAccountID;
	}
	
	public double getAmmount() {
		return ammount;
	}
	
	public Date getDate() {
		return date;
	}
	
}
