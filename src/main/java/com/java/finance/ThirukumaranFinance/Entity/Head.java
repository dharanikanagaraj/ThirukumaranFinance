package com.java.finance.ThirukumaranFinance.Entity;

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
@Table(name = "head", schema = "public")
public class Head {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HEAD_SEQ") // needs to create sequence
    @SequenceGenerator(name = "HEAD_SEQ", sequenceName = "HEAD_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;
	
	@Column(name = "head_name")
	private String headName;
	
	@Column(name = "extra_head")
	private boolean extraHead;
	

}
