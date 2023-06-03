package com.java.finance.ThirukumaranFinance.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "daily_total", schema = "public")
public class DailyTotal {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_TOTAL_SEQ") // needs to create sequence
    @SequenceGenerator(name = "DAILY_TOTAL_SEQ", sequenceName = "DAILY_TOTAL_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;
	
	@Column(name = "total_amount")
	private long totalAmount;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "line_id")
	private String lineId;

	

}
