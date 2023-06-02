package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.BillNotPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportCollectionResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.PastDateBillResponse;
import com.java.finance.ThirukumaranFinance.Repository.DailyCollectionRepository;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final LoneRepository loneRepository;
	private final DailyCollectionRepository dailyCollectionRepository;

	public List<IndividualReportLoanResponse> getActiveLoan(String lineId) {
		var response = new ArrayList<IndividualReportLoanResponse>();
		var loanData = loneRepository.getAllActiveLoanForReport(lineId);
		if(!loanData.isEmpty()) {
			for(int i=0;i<loanData.size();i++) {
				IndividualReportLoanResponse data = new IndividualReportLoanResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());	
				data.setAddress(loanData.get(i).getAddress());
				data.setPhoneNo(loanData.get(i).getPhoneNo());
				data.setLoanAmount(loanData.get(i).getLoanAmount());
				data.setLoanDate(loanData.get(i).getCurrentLoanDate().toString());
				data.setCloseDate(loanData.get(i).getAppxLoanClosedDate().toString());
				response.add(data);
				}
		}
		return response;
	}

	public List<IndividualReportCollectionResponse> getAllDailyCollectionForLoan(String lineId, String loanNo) {
		var response = new ArrayList<IndividualReportCollectionResponse>();
		var loandata = loneRepository.findByLoanNoAndLineId(lineId, loanNo);
		if(loandata != null) {
			var dailyCollection = dailyCollectionRepository.getAllByLoanId(loandata.getLoanId());
			if(!dailyCollection.isEmpty()) {
				for(int i=0;i<dailyCollection.size();i++) {
				IndividualReportCollectionResponse data = new IndividualReportCollectionResponse();
				data.setId(dailyCollection.get(i).getId());
				data.setDate(dailyCollection.get(i).getDate().toString());
				data.setBillAmount(dailyCollection.get(i).getAmountPaid());
				response.add(data);
				}
			}
		}
		return response;
	}

	public List<PastDateBillResponse> getAllPastDateBill(String lineId, String date) {
		var response = new ArrayList<PastDateBillResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var dailyCollection = dailyCollectionRepository.getAllDailyCollection(lineId, parsedDate);
		if (!dailyCollection.isEmpty()) {
			for (int i = 0; i < dailyCollection.size(); i++) {
				PastDateBillResponse data = new PastDateBillResponse();
				data.setLoanNo(dailyCollection.get(i).getLoan().getLoanNo());
				data.setName(dailyCollection.get(i).getLoan().getName());
				data.setAddress(dailyCollection.get(i).getLoan().getAddress());
				data.setBillAmount(dailyCollection.get(i).getAmountPaid());
				data.setExcess(dailyCollection.get(i).getLoan().getExcessAmount());
				response.add(data);
			}
		}
		return response;
	}

	public List<BillNotPaidResponse> getAllBillNotPaid(String lineId, String date) {
		var response = new ArrayList<BillNotPaidResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var dailyCollection = dailyCollectionRepository.getAllDailyCollectionNotPaid(lineId, parsedDate);
		if (!dailyCollection.isEmpty()) {
			for (int i = 0; i < dailyCollection.size(); i++) {
				BillNotPaidResponse data = new BillNotPaidResponse();
				data.setLoanNo(dailyCollection.get(i).getLoan().getLoanNo());
				data.setName(dailyCollection.get(i).getLoan().getName());
				data.setAddress(dailyCollection.get(i).getLoan().getAddress());
				data.setBillAmount(dailyCollection.get(i).getAmountPaid());
				var payamount = Integer.parseInt(dailyCollection.get(i).getLoan().getLoanAmount()) / 100;
				data.setPayAmount(String.valueOf(payamount));
				data.setDate(date);
				response.add(data);
			}
		}
		return response;
	}

	public List<BillNotPaidResponse> getExcessAmount(String lineId) {
		var response = new ArrayList<BillNotPaidResponse>();
		var loanData = loneRepository.getLoanWithExcessAmount(lineId);
		if (!loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				BillNotPaidResponse data = new BillNotPaidResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setAddress(loanData.get(i).getAddress());
				data.setExcess(loanData.get(i).getExcessAmount());
				data.setDate(loanData.get(i).getLoanClosedDate().toString());
				var dailyCollection = dailyCollectionRepository.getByLoanId(loanData.get(i).getLoanId(), loanData.get(i).getLoanClosedDate());
				if (dailyCollection != null) {
					data.setPayAmount(dailyCollection.getAmountPaid());
				}
				response.add(data);
			}
		}
		return response;
	}
}
