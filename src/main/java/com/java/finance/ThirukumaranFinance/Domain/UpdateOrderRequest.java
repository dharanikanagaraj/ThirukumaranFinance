package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class UpdateOrderRequest {
	
	private String loanNo;

	private String lineId;
	
	private String orderNo;
	
}
