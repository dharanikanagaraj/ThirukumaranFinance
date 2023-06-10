package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.AdminIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.AdminRequest;
import com.java.finance.ThirukumaranFinance.Domain.AdminUpdateRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Entity.Admin;
import com.java.finance.ThirukumaranFinance.Service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/create")
	public GenericResponse createAdmin(@RequestBody AdminRequest adminRequest) {
		var response = adminService.createAdmin(adminRequest);
		return response;
	}

	@PutMapping("/update")
	public GenericResponse updateAdmin(@RequestBody AdminUpdateRequest adminRequest) {
		var response = adminService.updateAdmin(adminRequest);
		return response;
	}

	@DeleteMapping("/delete")
	public GenericResponse deleteAdmin(@RequestBody AdminIdRequest adminRequest) {
		var response = adminService.deleteAdmin(adminRequest.getAdminId());
		return response;
	}

	@PostMapping("/particular/admin")
	public Admin getParticularAdmin(@RequestBody AdminIdRequest adminRequest) {
		var response = adminService.getParticularAdmin(adminRequest.getAdminId());
		return response;
	}
	
	@GetMapping("/all")
	public List<Admin> getAllAdmin() {
		var response = adminService.getAllAdmin();
		return response;
	}
}
