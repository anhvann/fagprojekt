package model;
import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {

	private String name, currency, accountID, accountID2;
	private BigDecimal amount, balance;
	private Date date;
	
	//Transfer
	public Transaction(String name, Date date, BigDecimal amount, String currency, String accountID, String accountID2, BigDecimal balance) {
		this.name = name;
		this.amount = amount;
		this.currency = currency;
		this.date = date;
		this.accountID = accountID;
		this.accountID2 = accountID2;
		this.balance = balance;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getAmount() {
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
	
	public BigDecimal getBalance(){
		return balance;
	}
}
