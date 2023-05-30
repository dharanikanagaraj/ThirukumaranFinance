package com.java.finance.ThirukumaranFinance.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "line_details", schema = "public")
public class Line {

	private static final long serialversionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "line_id")
	private String lineId; // sent from UI which should be next and next in serial order (eg:Ln01) primary
							// key

	@Column(name = "line_name")
	private String lineName;

	@Column(name = "created_on")
	private LocalDate createdOn;

	@Column(name = "updated_on")
	private LocalDate updatedOn;

}
