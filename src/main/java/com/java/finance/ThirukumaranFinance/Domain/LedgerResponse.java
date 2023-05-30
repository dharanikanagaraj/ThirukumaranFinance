package com.java.finance.ThirukumaranFinance.Domain;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LedgerResponse extends DailyCollectionData {
	
	private Map<String,String> date;
	
}
