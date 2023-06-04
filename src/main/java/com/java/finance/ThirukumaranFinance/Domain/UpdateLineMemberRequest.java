package com.java.finance.ThirukumaranFinance.Domain;

import lombok.Data;

@Data
public class UpdateLineMemberRequest {
	
	private String lineMemId;

	private String memberName;
	
	private String phoneNo;

	private String address;

	private String password;
}
