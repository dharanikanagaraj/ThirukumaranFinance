package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class NipResponse {
	
	private String loanNo;

	private String name;
	
	private String address;
	
	private String loanDate;
	
	private String closeDate;
	
	private int loanAmount;
	
	private int paidAmount;
	
	private int balance;
	
	private long payDayss;
	
}
