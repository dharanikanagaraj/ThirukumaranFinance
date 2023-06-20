package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.BalanceData;
import com.java.finance.ThirukumaranFinance.Domain.BalanceRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.HeadDataRequest;
import com.java.finance.ThirukumaranFinance.Domain.HeadRequest;
import com.java.finance.ThirukumaranFinance.Domain.IdRequest;
import com.java.finance.ThirukumaranFinance.Domain.IndividualHeadRequest;
import com.java.finance.ThirukumaranFinance.Domain.NameDateRequest;
import com.java.finance.ThirukumaranFinance.Domain.ThittamDateRequest;
import com.java.finance.ThirukumaranFinance.Domain.ThittamResponse;
import com.java.finance.ThirukumaranFinance.Entity.Head;
import com.java.finance.ThirukumaranFinance.Entity.OutStandingBalance;
import com.java.finance.ThirukumaranFinance.Entity.ThittamData;
import com.java.finance.ThirukumaranFinance.Service.ThittamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/thittam")
@RequiredArgsConstructor
public class ThittamController {

	private final ThittamService thittamService;

	@PostMapping("/create/head") // add head
	public GenericResponse createUpdateHead(@RequestBody HeadRequest request) {
		var response = thittamService.createUpdateHead(request.getId(),request.getName(),request.isUpdate(),true);
		return response;
	}
	
	@DeleteMapping("/delete/head") // delete 
	public GenericResponse deleteHead(@RequestBody IdRequest request) {
		var response = thittamService.deleteHead(request.getId());
		return response;
	}
	
	@GetMapping("/all/head") //get all head
	public List<Head> getAllHead() {
		var response = thittamService.getAllHead();
		return response;
	}
	
	@PostMapping("/create/headData") // create data for thittam
	public GenericResponse createHeadData(@RequestBody HeadDataRequest request) {
		var response = thittamService.createHeadData(request);
		return response;
	}
	
	@PostMapping("/thittamData/extraHead") // for that below table
	public List<HeadDataRequest> getThittamDataForExtraHead(@RequestBody ThittamDateRequest request) {
		var response = thittamService.getThittamDataForExtraHead(request);
		return response;
	}
	
	@PostMapping("/thittamData/all") // for main table
	public ThittamResponse getAllThittamData(@RequestBody ThittamDateRequest request) {
		var response = thittamService.getAllThittamData(request);
		return response;
	}
	
	@DeleteMapping("/delete/headData") // delete particular data in below table 
	public GenericResponse deleteHeadData(@RequestBody NameDateRequest request) {
		var response = thittamService.deleteHeadData(request.getName(), request.getDate());
		return response;
	}
	
	@PostMapping("/create/balance") // creating opening balance and closing balance on each day while creating send name always as Closing balance
	public GenericResponse createBalance(@RequestBody BalanceRequest request) {
		var response = thittamService.createBalance(request);
		return response;
	}
	
	@DeleteMapping("/delete/all") // delete all for that particular date
	public GenericResponse deleteAllExtraHeadForDate(@RequestBody ThittamDateRequest request) {
		var response = thittamService.deleteAllExtraHeadForDate(request.getDate());
		return response;
	}
	
	@PostMapping("/outStanding/balance")
	public List<OutStandingBalance> getAllOutStandingBalance(@RequestBody ThittamDateRequest request) {
		var response = thittamService.getAllOutStandingBalance(request.getDate());
		return response;
	}
	
	@PostMapping("/account")
	public List<ThittamData> getAllAccountData(@RequestBody DateRequest request) {
		var response = thittamService.getAllAccountData(request);
		return response;
	}
	
	@PostMapping("/individualhead")
	public List<ThittamData> getAllIndividualHeadData(@RequestBody IndividualHeadRequest request) {
		var response = thittamService.getAllIndividualHeadData(request);
		return response;
	}
	
	@PostMapping("/balanceSheet")
	public List<BalanceData> getAlldataforBalanceSheet(@RequestBody DateRequest request) {
		var response = thittamService.getAlldataforBalanceSheet(request);
		return response;
	}
	
	@GetMapping("/trialSheet")
	public List<BalanceData> getAlldataforTrialSheet() {
		var response = thittamService.getAlldataforTrailSheet();
		return response;
	}
	
	@GetMapping("/openingbalance")
	public GenericResponse getOpeningBalance() {
		var response = thittamService.getOpeningBalance();
		return response;
	}
	
	@GetMapping("/dateForThittam")
	public GenericResponse getThittamDate() {
		var response = thittamService.getThittamDate();
		return response;
	}


}
