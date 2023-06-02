package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BillNotPaidResponse extends PastDateBillResponse{
	
	private String payAmount;
	
	private String date;
	
}
