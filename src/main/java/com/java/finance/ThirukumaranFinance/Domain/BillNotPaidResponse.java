package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BillNotPaidResponse extends PastDateBillResponse{
	
	private int payAmount;
	
	private String date;
	
}
