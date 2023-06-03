package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class BillEntryResponse {
	
	private String loanNo;

	private String name;
	
	private int billAmount;
	
	private int excess;
	
	private String time;
	
}
