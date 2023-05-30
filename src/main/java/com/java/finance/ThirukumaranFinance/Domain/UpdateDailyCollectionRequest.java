package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class UpdateDailyCollectionRequest {
	
	private String loanNo;

	private String newAmountPaid;
	
	private String date;
	
	private String lineId;
	
	private String oldAmount;
	
}
