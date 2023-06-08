package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class ClosedPartyResponse {
	
	private String loanNo;

	private String name;
	
	private String address;
	
	private String userNo;
	
	private String phoneNo;
	
	private int loanAmount;
	
	private String date;
	
	private String closeDate;
	
	private int interest;
	
}
