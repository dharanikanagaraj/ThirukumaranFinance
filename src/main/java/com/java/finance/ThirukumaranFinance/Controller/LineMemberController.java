package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.LineMemberIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.LineMemberRequest;
import com.java.finance.ThirukumaranFinance.Entity.LineMember;
import com.java.finance.ThirukumaranFinance.Service.LineMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/lineMember")
@RequiredArgsConstructor
public class LineMemberController {

	private final LineMemberService lineMemberService;

	@PostMapping("/create")
	public String createLineMember(@RequestBody LineMemberRequest lineMemberRequest) {
		var response = lineMemberService.createLineMember(lineMemberRequest);
		return response;
	}

	@PutMapping("/update")
	public String updateLineMember(@RequestBody LineMemberRequest lineMemberRequest) {
		var response = lineMemberService.updateLineMember(lineMemberRequest);
		return response;
	}

	@DeleteMapping("/delete")
	public String deleteLineMember(@RequestBody LineMemberIdRequest lineMemberIdRequest) {
		var response = lineMemberService.deleteLineMember(lineMemberIdRequest.getLineMemId());
		return response;
	}

	@PostMapping("/particular/linemember")
	public LineMember getParticularLineMember(@RequestBody LineMemberIdRequest lineMemberIdRequest) {
		var response = lineMemberService.getParticularLineMember(lineMemberIdRequest.getLineMemId());
		return response;
	}

	@GetMapping("/all")
	public List<LineMember> getAllLineMember() {
		var response = lineMemberService.getAllLineMember();
		return response;
	}
}
