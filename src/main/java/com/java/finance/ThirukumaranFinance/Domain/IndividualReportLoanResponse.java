package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class IndividualReportLoanResponse {
	
	private String loanNo;

	private String name;
	
	private int loanAmount;
	
	private String loanDate;
	
	private String closeDate;
	
	private String phoneNo;
	
	private String address;
	
	private String orderNo;
	
}
