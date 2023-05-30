package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.BillEntryResponse;
import com.java.finance.ThirukumaranFinance.Domain.DailyCollectionData;
import com.java.finance.ThirukumaranFinance.Domain.DailyCollectionRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.LedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateDailyCollectionRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateDailyCollectionResponse;
import com.java.finance.ThirukumaranFinance.Service.DailyCollectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/dailycollection")
@RequiredArgsConstructor
public class DailyCollectionController {
	
	private final DailyCollectionService dailyCollectionService;

	@GetMapping("/all/activeloan") // direct entry page get call
	public List<DailyCollectionData> getAllActiveLoan(@NonNull @RequestBody LineIdRequest lineIdRequest) {
		var response = dailyCollectionService.getAllActiveLoan(lineIdRequest.getLineId());
		return response;
	}
	
	@GetMapping("/all/activeloan/ledger") // ledger entry page get call
	public List<LedgerResponse> getAllActiveLoanLedger(@RequestBody LineIdRequest lineIdRequest) {
		var response = dailyCollectionService.getAllActiveLoanLedger(lineIdRequest.getLineId());
		return response;
	}
	
	@GetMapping("/particularcollection") // get call for edit daily collection
	public UpdateDailyCollectionResponse getparticularCollection(@RequestBody DailyCollectionRequest request) {
		var response = dailyCollectionService.getparticularCollection(request);
		return response;
	}
	
	@PostMapping("/create")
	public String createDailyCollection(@RequestBody DailyCollectionRequest request) {
		var response = dailyCollectionService.createDailyCollection(request);
		return response;
	}
	
	@PutMapping("/update")
	public String updateDailyCollection(@RequestBody UpdateDailyCollectionRequest request) {
		var response = dailyCollectionService.updateDailyCollection(request);
		return response;
	}
	
	@GetMapping("/billentry") // direct entry page get call
	public List<BillEntryResponse> getAllDailyCollection(@RequestBody DateCloseRequest request) {
		var response = dailyCollectionService.getAllDailyCollection(request);
		return response;
	}

}
