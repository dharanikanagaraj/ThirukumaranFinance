package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class PastDateBillResponse {
	
	private String loanNo;

	private String name;
	
	private int billAmount;
	
	private int excess;
	
	private String address;
	
}
