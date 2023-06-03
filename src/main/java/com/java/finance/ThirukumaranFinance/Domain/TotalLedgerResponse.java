package com.java.finance.ThirukumaranFinance.Domain;

import java.util.List;

import lombok.Data;

@Data
public class TotalLedgerResponse {
	
	private int loanCount;
	
	private List<NipResponse> loanData;
	
}
