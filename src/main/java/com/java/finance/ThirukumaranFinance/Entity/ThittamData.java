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
@Table(name = "thittam_data", schema = "public")
public class ThittamData {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THITTAM_DATA_SEQ") // needs to create sequence
    @SequenceGenerator(name = "THITTAM_DATA_SEQ", sequenceName = "THITTAM_DATA_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "debit")
	private int debit;

	@Column(name = "credit")
	private int credit;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "extra_head")
	private boolean extraHead;
	
	@Column(name = "balance")
	private int balance;
	

}
