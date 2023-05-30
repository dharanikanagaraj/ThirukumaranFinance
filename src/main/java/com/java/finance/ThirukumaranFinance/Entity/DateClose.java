package com.java.finance.ThirukumaranFinance.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "date_close", schema = "public")
public class DateClose {
	
	private static final long serialversionUID = 1L;
	
	@Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATE_CLOSE_SEQ") // needs to create sequence
    @SequenceGenerator(name = "DATE_CLOSE_SEQ", sequenceName = "DATE_CLOSE_SEQ", allocationSize = 1)
    @Column(name = "id")
    private int id;
	
	@Column(name = "date")
	private LocalDate date;
	
    @Column(name ="line_id")
    private String lineId;
	
	@Column(name = "created_on")
	private LocalDate createdOn;
	
	

}
