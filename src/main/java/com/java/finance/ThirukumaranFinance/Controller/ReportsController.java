package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.BillNotPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.BulkPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.ClosedPartyResponse;
import com.java.finance.ThirukumaranFinance.Domain.ContinuouslyNotPaidRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateRequest;
import com.java.finance.ThirukumaranFinance.Domain.DeleteLoanRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportCollectionResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.LedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.MonthlyLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.NipResponse;
import com.java.finance.ThirukumaranFinance.Domain.PastDateBillResponse;
import com.java.finance.ThirukumaranFinance.Domain.TotalLedgerRequest;
import com.java.finance.ThirukumaranFinance.Domain.TotalLedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.UpdateOrderRequest;
import com.java.finance.ThirukumaranFinance.Domain.UserRequest;
import com.java.finance.ThirukumaranFinance.Domain.UserResponse;
import com.java.finance.ThirukumaranFinance.Service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/reports")
@RequiredArgsConstructor
public class ReportsController {
    
    private final ReportService reportService;
    
    @PostMapping("/individualReport/loan") // can be used in both individual report and order number page
    public List<IndividualReportLoanResponse> getActiveLoan(@RequestBody LineIdRequest request) {
		var response = reportService.getActiveLoan(request.getLineId());
		return response;
	}
    
    @PostMapping("/individualReport/dailycollection")
    public List<IndividualReportCollectionResponse> getAllDailyCollectionForLoan(@RequestBody DeleteLoanRequest request) {
		var response = reportService.getAllDailyCollectionForLoan(request.getLineId(),request.getLoanNo());
		return response;
	}
    
    @PostMapping("/pastDateBill")
    public List<PastDateBillResponse> getAllPastDateBill(@RequestBody DateCloseRequest request) {
		var response = reportService.getAllPastDateBill(request.getLineId(),request.getDate());
		return response;
	}
    
    @PostMapping("/billNotPaid")
    public List<BillNotPaidResponse> getAllBillNotPaid(@RequestBody DateCloseRequest request) {
		var response = reportService.getAllBillNotPaid(request.getLineId(),request.getDate());
		return response;
	}
    
    @PostMapping("/excess")
    public List<BillNotPaidResponse> getExcessAmount(@RequestBody LineIdRequest request) {
		var response = reportService.getExcessAmount(request.getLineId());
		return response;
	}
    
    @PostMapping("/monthlyBill")
    public List<IndividualReportCollectionResponse> getMonthlyBill(@RequestBody DateRequest request) {
		var response = reportService.getMonthlyBill(request.getLineId(),request.getStartDate(),request.getEndDate());
		return response;
	}
    
    @PostMapping("/closedParty")
    public List<ClosedPartyResponse> getAllClosedParty(@RequestBody LineIdRequest request) {
		var response = reportService.getAllClosedParty(request.getLineId());
		return response;
	}
    
    @PostMapping("/userList")
    public List<UserResponse> getAllUserList(@RequestBody LineIdRequest request) {
		var response = reportService.getAllUserList(request.getLineId());
		return response;
	}
    
    @PostMapping("/user/pastLoan")
    public List<ClosedPartyResponse> getAllClosedLoanForUser(@RequestBody UserRequest request) {
		var response = reportService.getAllClosedLoanForUser(request.getLineId(),request.getUserNo());
		return response;
	}
    
    @PutMapping("/update/orderNo")
    public GenericResponse updateOrderNo(@RequestBody UpdateOrderRequest request) {
		var response = reportService.updateOrderNo(request.getLineId(),request.getLoanNo(),request.getOrderNo());
		return response;
	}
    
    @PostMapping("/ContinouslyNotPaid")
    public List<ClosedPartyResponse> getContinuoslyNotPaidUser(@RequestBody ContinuouslyNotPaidRequest request) {
		var response = reportService.getContinuoslyNotPaidUser(request.getNumber(),request.getLineId());
		return response;
	}
    
    @PostMapping("/ledger")
    public List<LedgerResponse> getLoanForLedger(@RequestBody DateRequest request) {
		var response = reportService.getLoanForLedger(request.getLineId(),request.getStartDate(),request.getEndDate());
		return response;
	}
    
    @PostMapping("/nipParty")
    public List<NipResponse> getNipParty(@RequestBody ContinuouslyNotPaidRequest request) {
		var response = reportService.getNipParty(request.getLineId(),request.getNumber());
		return response;
	}
    
    @PostMapping("/bulkPaid")
    public List<BulkPaidResponse> getBulkPaidLoan(@RequestBody DateRequest request) {
		var response = reportService.getBulkPaidLoan(request.getLineId(),request.getStartDate(),request.getEndDate());
		return response;
	}

    @PostMapping("/totalLedger/all")
    public TotalLedgerResponse getLoanForLedger(@RequestBody LineIdRequest request) {
    	var response = reportService.getLoanForLedger(request.getLineId());
		return response;
    }
    
    @PostMapping("/totalLedger/dateRange")
    public TotalLedgerResponse getLoanForLedgerForDateRange(@RequestBody TotalLedgerRequest request) {
    	var response = reportService.getLoanForLedgerForDateRange(request.getLineId(),request.getDateRange());
		return response;
    }
    
    @PostMapping("/monthlyLoan")
    public MonthlyLoanResponse getMonthlyLoan(@RequestBody DateRequest request) {
    	var response = reportService.getMonthlyLoan(request.getLineId(),request.getStartDate(),request.getEndDate());
		return response;
    }
}
