package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class BillEntryResponse {
	
	private String loanNo;

	private String name;
	
	private String billAmount;
	
	private String excess;
	
	private String time;
	
}
