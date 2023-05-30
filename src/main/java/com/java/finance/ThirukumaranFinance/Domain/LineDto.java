package com.java.finance.ThirukumaranFinance.Domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LineDto {
	
	private LocalDate date;

	private String lineId;
	
	private String lineName;
}
