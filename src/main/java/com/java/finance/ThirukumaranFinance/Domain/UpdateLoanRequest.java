package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class UpdateLoanRequest {

	private String loanNo;

	private String userNo;
	
	private String name;
	
	private String address;
	
	private String phoneNo;
	
	private String orderNo;
	
	private String currentDate;
	
	private String loanAmount;
	
	private String seetuAmount;
	
	private String commissionAmount;
	
	private String interest;
	
	private String lineId;
	
	
	
}
