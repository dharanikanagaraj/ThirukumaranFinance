package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.DeleteLoanRequest;
import com.java.finance.ThirukumaranFinance.Domain.LoanRequest;
import com.java.finance.ThirukumaranFinance.Entity.Loan;
import com.java.finance.ThirukumaranFinance.Service.LoneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/loan")
@RequiredArgsConstructor
public class LoanController {
	
	private final LoneService loneService;

	@PostMapping("/create")
	public String createLoan(@RequestBody LoanRequest loneRequest) {
		var response = loneService.createLone(loneRequest);
		return response;
	}
	
	@PutMapping("/update")
	public String updateLoan(@RequestBody LoanRequest loneRequest) {
		var response = loneService.updateLone(loneRequest);
		return response;
	}
	
	@DeleteMapping("/delete")
	public String deleteLoan(@RequestBody DeleteLoanRequest deleteLoanRequest) {
		var response = loneService.deleteLoan(deleteLoanRequest.getLineId(),deleteLoanRequest.getLoanNo());
		return response;
	}
	
	@PostMapping("/particular/loanNo")// fetch loan for given loan no and line Id
	public Loan getParticularLoan(@RequestBody DeleteLoanRequest deleteLoanRequest) {
		var response = loneService.getParticularLoan(deleteLoanRequest.getLineId(),deleteLoanRequest.getLoanNo());
		return response;
	}
	
	@PostMapping("/particular/date")// active loan for particular date
	public List<Loan> getAllTodayLoan(@RequestBody DateCloseRequest request) {
		var response = loneService.getAllTodayLoan(request.getLineId(),request.getDate());
		return response;
	}

}
