package com.java.finance.ThirukumaranFinance.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "admin", schema = "public")
public class Admin {
	
	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "admin_id")
	private String adminId; // sent from UI which should be next and next in serial order (eg:Ad01)
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "phone_no") //unique
	private String phoneNo;
	
	@Column(name = "password")
	private String password;
	
	

}
