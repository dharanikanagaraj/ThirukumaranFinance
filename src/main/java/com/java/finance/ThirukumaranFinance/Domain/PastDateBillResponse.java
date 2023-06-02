package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class PastDateBillResponse {
	
	private String loanNo;

	private String name;
	
	private String billAmount;
	
	private String excess;
	
	private String address;
	
}
