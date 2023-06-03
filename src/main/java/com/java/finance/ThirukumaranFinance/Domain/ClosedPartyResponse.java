package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class ClosedPartyResponse {
	
	private String loanNo;

	private String name;
	
	private String address;
	
	private int loanAmount;
	
	private String date;
	
	private int interest;
	
}
