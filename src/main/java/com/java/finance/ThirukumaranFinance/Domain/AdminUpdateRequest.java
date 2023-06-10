package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class AdminUpdateRequest{
	
	private String userName;
	
	private String phoneNo;
	
	private String newPassword;
	
	private String oldPassword;
	
	private String adminId;
	
}
