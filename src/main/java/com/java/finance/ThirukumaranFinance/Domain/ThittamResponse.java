package com.java.finance.ThirukumaranFinance.Domain;

import java.util.List;

import lombok.Data;

@Data
public class ThittamResponse{
	
	private int openingBalance;
	
	private String date;
	
	private String prevDate;
	
	private List<HeadDataRequest> thittamList;
}
