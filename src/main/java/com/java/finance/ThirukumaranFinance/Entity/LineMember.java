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
@Table(name = "line_member_details", schema = "public")
public class LineMember {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "linMem_id")
	private String linMemId; // sent from UI which should be next and next in serial order (eg:Lm01)
	
	@Column(name = "member_name")
	private String memberName;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "created_on")
	private LocalDate createdOn;
	
	@Column(name = "updated_on")
	private LocalDate updatedOn;
	
	@Column(name= "role")
	private String role; // add admin details in same table with access as Admin. login validation is in this table.
	
	

}
