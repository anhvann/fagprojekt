package model;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;

public class Account {

	private String accountID, ISOCode, name;
	private LinkedList<String> owners;
	private BigDecimal balance, interest;
	private ArrayList<Transaction> transactions;
	
	public Account(LinkedList<String> owners, String accountID, String name, BigDecimal balance, BigDecimal interest, String ISOCode, ArrayList<Transaction> transactions) {
		this.owners = owners;
		this.accountID = accountID;
		this.name = name;
		this.interest = interest;
		this.ISOCode = ISOCode;
		this.transactions = new ArrayList<>();
		this.balance = balance;
		this.transactions = transactions;
		if (transactions != null && transactions.size() > 0) {
			this.balance = transactions.get(transactions.size() - 1).getBalance();
		}
	}
	
	public BigDecimal getBalance(){
		 return balance;
	}
	
	//Convert BigDecimal to String
	public String getBalanceString(){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("###,##0.00", symbols);
		return formatter.format(balance.doubleValue());
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
	
	public String getISOCode() {
		return ISOCode;
	}
	
	public LinkedList<String> getOwners() {
		return owners;
	}
	
	public void setISOCode(String ISOCode) {
		this.ISOCode = ISOCode;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		if (transactions.size() > 0) {
			this.balance = transactions.get(transactions.size() - 1).getBalance();
		}
	}
}