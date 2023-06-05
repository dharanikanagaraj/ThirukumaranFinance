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
@Table(name = "outstanding_balance", schema = "public")
public class OutStandingBalance {

	private static final long serialversionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OUTSTANDING_BALANCE_SEQ") // needs to create sequence
    @SequenceGenerator(name = "OUTSTANDING_BALANCE_SEQ", sequenceName = "OUTSTANDING_BALANCE_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;

	@Column(name = "date")
	private LocalDate date; 						

	@Column(name = "line_name")
	private String lineName;

	@Column(name = "below120_count")
	private int firstCount;

	@Column(name = "below120_balance")
	private int firstBalance;
	
	@Column(name = "120to240_count")
	private int secondCount;
	
	@Column(name = "120to240_balance")
	private int secondBalance;
	
	@Column(name = "240to365_count")
	private int thirdCount;
	
	@Column(name = "240to365_balance")
	private int thirdBalance;
	
	@Column(name = "above365_count")
	private int fourthCount;
	
	@Column(name = "above365_balance")
	private int fourthBalance;

}
