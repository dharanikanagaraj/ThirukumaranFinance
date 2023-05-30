package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class LineMemberRequest {
	
	private String lineMemId;

	private String memberName;
	
	private String phoneNo;

	private String address;

	private String password;
}
