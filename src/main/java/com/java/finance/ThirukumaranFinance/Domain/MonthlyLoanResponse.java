package com.java.finance.ThirukumaranFinance.Domain;

import java.util.List;

import lombok.Data;

@Data
public class MonthlyLoanResponse {

	private List<ClosedPartyResponse> loanData;

	private int loanAmountTotal;

	private int interestTotal;

}
