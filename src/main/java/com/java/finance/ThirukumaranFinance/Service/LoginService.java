package com.java.finance.ThirukumaranFinance.Service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Repository.AdminRepository;
import com.java.finance.ThirukumaranFinance.Repository.LineMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final LineMemberRepository lineMemberRepository;
	
	private final AdminRepository adminRepository;


	public boolean validate(String username, String password) {
		var details=lineMemberRepository.findByPhoneNo(username);
		String passwordhash=details.getPassword();
		if (passwordhash.equalsIgnoreCase(password))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean validateAdmin(String username, String password) {
		var details=adminRepository.findByPhoneNo(username);
		String passwordhash=details.getPassword();
		if (BCrypt.checkpw(password, passwordhash))
		{
			return true;
		}
		else {
			return false;
		}
	}

}
