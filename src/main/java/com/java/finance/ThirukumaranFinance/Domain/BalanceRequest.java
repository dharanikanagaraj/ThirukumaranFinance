package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BalanceRequest extends NameDateRequest{
	
	private int balance;
	
}
