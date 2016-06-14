package model;

import java.math.BigDecimal;

public class Currency {
	private String ISO;
	private String country;
	private BigDecimal buyRate, sellRate;
	
	public Currency(String ISO, String country, BigDecimal buyRate, BigDecimal sellRate){
		this.ISO = ISO;
		this.country = country;
		this.buyRate = buyRate;
		this.sellRate = sellRate;
	}
	
	public String getISO(){
		return ISO;
	}
	
	public String getCountry(){
		return country;
	}
	public String getBuyRate(){
		return buyRate.toString();
	}
	public String getSellRate(){
		return sellRate.toString();
	}
}
