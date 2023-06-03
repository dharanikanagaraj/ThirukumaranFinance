package com.java.finance.ThirukumaranFinance.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "daily_amount_collection", schema = "public")
public class Dailycollection {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DAILY_AMOUNT_COLLECTION_SEQ") // needs to create sequence
    @SequenceGenerator(name = "DAILY_AMOUNT_COLLECTION_SEQ", sequenceName = "DAILY_AMOUNT_COLLECTION_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;
	
	@Column(name = "amount_paid")
	private long amountPaid;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "line_id")
	private String lineId;

	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;
	
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY) 
    @JoinColumn(name ="loan_id")
    private Loan loan;
	

}
