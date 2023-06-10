package com.java.finance.ThirukumaranFinance.Service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.AdminRequest;
import com.java.finance.ThirukumaranFinance.Domain.AdminUpdateRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Entity.Admin;
import com.java.finance.ThirukumaranFinance.Repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;

	public GenericResponse createAdmin(AdminRequest adminRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var adminDto = new Admin();
			adminDto.setAdminId(getLatestSequenceForAdmin());
			adminDto.setUserName(adminRequest.getUserName());
			adminDto.setPhoneNo(adminRequest.getPhoneNo());
			adminDto.setPassword(hashPassword(adminRequest.getPassword()));
			adminRepository.save(adminDto);
			genericResponse.setMessage("Admin  created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create admin");
			return genericResponse;
		}
	}
	
	public String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public String getLatestSequenceForAdmin() {
		String newValue;
		String originalValue = adminRepository.findSequence();
		if (originalValue != null && !originalValue.isBlank()) {
			// Extract the numeric part of the string
			String numericPart = originalValue.substring(2);
			// Convert the numeric part to an integer and increment by one
			int incrementedValue = Integer.parseInt(numericPart) + 1;
			// Combine the original prefix with the incremented value
			 newValue = originalValue.substring(0, 2) + String.format("%02d", incrementedValue);
			System.out.println("Incremented value: " + newValue);
		} else {
			 newValue ="Ad01";
		}
		return newValue;
	}

	public GenericResponse updateAdmin(AdminUpdateRequest adminRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = adminRepository.findByAdminId(adminRequest.getAdminId());
			if(validate(entity.getPassword(), adminRequest.getOldPassword())) {
				entity.setUserName(adminRequest.getUserName());
				entity.setPhoneNo(adminRequest.getPhoneNo());
				entity.setPassword(hashPassword(adminRequest.getNewPassword()));
				adminRepository.save(entity);
			genericResponse.setMessage("Admin Updated Successfully");
			return genericResponse;
			}else {
				genericResponse.setMessage("Old Password is not correct");
				return genericResponse;	
			}
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to update Admin");
			return genericResponse;
		}
	}

	private boolean validate(String password, String oldPassword) {
		if (BCrypt.checkpw(oldPassword, password))
		{
			return true;
		}
		else {
			return false;
		}
	}

	public GenericResponse deleteAdmin(String adminId) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = adminRepository.findByAdminId(adminId);
			adminRepository.delete(entity);
			genericResponse.setMessage("Admin deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to delete admin");
			return genericResponse;
		}
	}

	public Admin getParticularAdmin(String adminId) {
		return adminRepository.findByAdminId(adminId);
	}

	public List<Admin> getAllAdmin() {
		var entity = adminRepository.findAll();
		return entity;
	}

}
