package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class DailyCollectionRequest {
	
	private String loanNo;

	private String amountPaid;
	
	private String date;
	
	private String lineId;
	
}
