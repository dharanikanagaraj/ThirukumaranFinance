package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BulkPaidResponse extends ClosedPartyResponse{
	
	private int BillAmount;
}
