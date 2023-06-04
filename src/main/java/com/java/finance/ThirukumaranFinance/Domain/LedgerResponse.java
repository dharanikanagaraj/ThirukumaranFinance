package com.java.finance.ThirukumaranFinance.Domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LedgerResponse extends DailyCollectionData {

	private List<DateValue> dateValue;

	private int preBalance;

	private int total;

}
