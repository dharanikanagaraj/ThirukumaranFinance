package com.java.finance.ThirukumaranFinance.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.BillNotPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateRequest;
import com.java.finance.ThirukumaranFinance.Domain.DeleteLoanRequest;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportCollectionResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.PastDateBillResponse;
import com.java.finance.ThirukumaranFinance.Service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/reports")
@RequiredArgsConstructor
public class ReportsController {
    
    private final ReportService reportService;
    
    @GetMapping("/individualReport/loan")
    public List<IndividualReportLoanResponse> getActiveLoan(@RequestBody LineIdRequest request) {
		var response = reportService.getActiveLoan(request.getLineId());
		return response;
	}
    
    @GetMapping("/individualReport/dailycollection")
    public List<IndividualReportCollectionResponse> getAllDailyCollectionForLoan(@RequestBody DeleteLoanRequest request) {
		var response = reportService.getAllDailyCollectionForLoan(request.getLineId(),request.getLoanNo());
		return response;
	}
    
    @GetMapping("/pastDateBill")
    public List<PastDateBillResponse> getAllPastDateBill(@RequestBody DateCloseRequest request) {
		var response = reportService.getAllPastDateBill(request.getLineId(),request.getDate());
		return response;
	}
    
    @GetMapping("/billNotPaid")
    public List<BillNotPaidResponse> getAllBillNotPaid(@RequestBody DateCloseRequest request) {
		var response = reportService.getAllBillNotPaid(request.getLineId(),request.getDate());
		return response;
	}
    
    @GetMapping("/excess")
    public List<BillNotPaidResponse> getExcessAmount(@RequestBody LineIdRequest request) {
		var response = reportService.getExcessAmount(request.getLineId());
		return response;
	}
    
    @GetMapping("/monthlyBill")
    public List<IndividualReportCollectionResponse> getMonthlyBill(@RequestBody DateRequest request) {
		var response = reportService.getMonthlyBill(request.getLineId(),request.getStartDate(),request.getEndDate());
		return response;
	}

    @GetMapping("/reports")
    public void dummy(@RequestParam String dummyDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(dummyDate, formatter);

        LocalDate belowTwoDaysDate = parsedDate.minusDays(2);
        LocalDate belowFourDaysDate = parsedDate.minusDays(4);

        System.out.println("2 days: "+belowTwoDaysDate+":::::::"+belowFourDaysDate);

        //lineRepository.findByTwoDate(belowTwoDaysDate, belowFourDaysDate);


//        YourEntity entity = new YourEntity();
//        entity.setDateColumn(parsedDate);
//
//        yourEntityRepository.save(entity);

    }

}
