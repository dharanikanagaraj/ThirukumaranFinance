package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class HeadDataRequest {
	
	private String name;

	private String description;
	
	private int credit;
	
	private int debit;
	
	private String date;
	
}
