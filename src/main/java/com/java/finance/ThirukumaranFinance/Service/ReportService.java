package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.BillNotPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.BulkPaidResponse;
import com.java.finance.ThirukumaranFinance.Domain.ClosedPartyResponse;
import com.java.finance.ThirukumaranFinance.Domain.DateValue;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportCollectionResponse;
import com.java.finance.ThirukumaranFinance.Domain.IndividualReportLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.LedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.MonthlyLoanResponse;
import com.java.finance.ThirukumaranFinance.Domain.NipResponse;
import com.java.finance.ThirukumaranFinance.Domain.PastDateBillResponse;
import com.java.finance.ThirukumaranFinance.Domain.TotalLedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.UserResponse;
import com.java.finance.ThirukumaranFinance.Entity.Dailycollection;
import com.java.finance.ThirukumaranFinance.Entity.Loan;
import com.java.finance.ThirukumaranFinance.Repository.DailyCollectionRepository;
import com.java.finance.ThirukumaranFinance.Repository.DailyTotalRepository;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final LoneRepository loneRepository;
	private final DailyCollectionRepository dailyCollectionRepository;
	private final DailyTotalRepository dailyTotalRepository;

	public List<IndividualReportLoanResponse> getActiveLoan(String lineId) {
		var response = new ArrayList<IndividualReportLoanResponse>();
		var loanData = loneRepository.getAllActiveLoanForReport(lineId);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				IndividualReportLoanResponse data = new IndividualReportLoanResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setAddress(loanData.get(i).getAddress());
				data.setPhoneNo(loanData.get(i).getPhoneNo());
				data.setLoanAmount(loanData.get(i).getLoanAmount());
				data.setLoanDate(loanData.get(i).getCurrentLoanDate().toString());
				data.setCloseDate(loanData.get(i).getAppxLoanClosedDate().toString());
				data.setOrderNo(loanData.get(i).getOrderNo());
				response.add(data);
			}
		}
		return response;
	}

	public List<IndividualReportCollectionResponse> getAllDailyCollectionForLoan(String lineId, String loanNo) {
		var response = new ArrayList<IndividualReportCollectionResponse>();
		var loandata = loneRepository.findByLoanNoAndLineId(lineId, loanNo);
		if (loandata != null && loandata != null) {
			var dailyCollection = dailyCollectionRepository.getAllByLoanId(loandata.getLoanId());
			if (!dailyCollection.isEmpty()) {
				for (int i = 0; i < dailyCollection.size(); i++) {
					IndividualReportCollectionResponse data = new IndividualReportCollectionResponse();
					data.setId(dailyCollection.get(i).getId());
					data.setDate(dailyCollection.get(i).getDate().toString());
					data.setBillAmount(String.valueOf(dailyCollection.get(i).getAmountPaid()));
					response.add(data);
				}
			}
		}
		return response;
	}

	public List<PastDateBillResponse> getAllPastDateBill(String lineId, String date) {
		var response = new ArrayList<PastDateBillResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var dailyCollection = dailyCollectionRepository.getAllDailyCollection(lineId, parsedDate);
		if (dailyCollection != null && !dailyCollection.isEmpty()) {
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
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var dailyCollection = dailyCollectionRepository.getAllDailyCollectionNotPaid(lineId, parsedDate);
		if (dailyCollection != null && !dailyCollection.isEmpty()) {
			for (int i = 0; i < dailyCollection.size(); i++) {
				BillNotPaidResponse data = new BillNotPaidResponse();
				data.setLoanNo(dailyCollection.get(i).getLoan().getLoanNo());
				data.setName(dailyCollection.get(i).getLoan().getName());
				data.setAddress(dailyCollection.get(i).getLoan().getAddress());
				data.setBillAmount(dailyCollection.get(i).getAmountPaid());
				var payamount = (dailyCollection.get(i).getLoan().getLoanAmount()) / 100;
				data.setPayAmount(payamount);
				data.setDate(date);
				response.add(data);
			}
		}
		return response;
	}

	public List<BillNotPaidResponse> getExcessAmount(String lineId) {
		var response = new ArrayList<BillNotPaidResponse>();
		var loanData = loneRepository.getLoanWithExcessAmount(lineId);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				BillNotPaidResponse data = new BillNotPaidResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setAddress(loanData.get(i).getAddress());
				data.setExcess(loanData.get(i).getExcessAmount());
				data.setDate(loanData.get(i).getLoanClosedDate().toString());
				var dailyCollection = dailyCollectionRepository.getByLoanId(loanData.get(i).getLoanId(),
						loanData.get(i).getLoanClosedDate());
				if (dailyCollection != null) {
					data.setPayAmount(dailyCollection.getAmountPaid());
				}
				response.add(data);
			}
		}
		return response;
	}

	public List<IndividualReportCollectionResponse> getMonthlyBill(String lineId, String startDate, String endDate) {
		var response = new ArrayList<IndividualReportCollectionResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startParsedDate = LocalDate.parse(startDate, formatters);
		LocalDate endParsedDate = LocalDate.parse(endDate, formatters);
		var totalData = dailyTotalRepository.findTotalForParticularRange(lineId, startParsedDate, endParsedDate);
		if (totalData != null && !totalData.isEmpty()) {
			for (int i = 0; i < totalData.size(); i++) {
				IndividualReportCollectionResponse data = new IndividualReportCollectionResponse();
				data.setId(totalData.get(i).getId());
				data.setDate(totalData.get(i).getDate().toString());
				data.setBillAmount(String.valueOf(totalData.get(i).getTotalAmount()));
				response.add(data);
			}
		}
		return response;
	}

	public List<ClosedPartyResponse> getAllClosedParty(String lineId) {
		var response = new ArrayList<ClosedPartyResponse>();
		var loanData = loneRepository.getAllClosedParty(lineId);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				ClosedPartyResponse data = new ClosedPartyResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setAddress(loanData.get(i).getAddress());
				data.setLoanAmount(loanData.get(i).getLoanAmount());
				data.setPhoneNo(loanData.get(i).getPhoneNo());
				data.setUserNo(loanData.get(i).getUserNo());
				response.add(data);
			}
		}
		return response;
	}

	public List<UserResponse> getAllUserList(String lineId) {
		var response = new ArrayList<UserResponse>();
		var loanData = loneRepository.getAllDistinctUser(lineId);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				UserResponse data = new UserResponse();
				data.setUserNo(loanData.get(i).getUserNo());
				data.setName(loanData.get(i).getName());
				response.add(data);
			}
		}
		return response;
	}

	public List<ClosedPartyResponse> getAllClosedLoanForUser(String lineId, String userNo) {
		var response = new ArrayList<ClosedPartyResponse>();
		var loanData = loneRepository.getAllClosedLoanForUser(lineId, userNo);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				ClosedPartyResponse data = new ClosedPartyResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setAddress(loanData.get(i).getAddress());
				data.setLoanAmount(loanData.get(i).getLoanAmount());
				data.setDate(loanData.get(i).getCurrentLoanDate().toString());
				data.setCloseDate(loanData.get(i).getAppxLoanClosedDate().toString());
				response.add(data);
			}
		}
		return response;
	}

	public GenericResponse updateOrderNo(String lineId, String loanNo, String orderNo) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var loanDto = loneRepository.findByLoanNoAndLineId(lineId, loanNo);
			loanDto.setOrderNo(orderNo);
			loneRepository.save(loanDto);
			genericResponse.setMessage("Order number updated Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to update Order number");
			return genericResponse;
		}
	}

	public List<ClosedPartyResponse> getContinuoslyNotPaidUser(int number, String lineId) {
		var response = new ArrayList<ClosedPartyResponse>();

		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(number);
		var loanData = loneRepository.getAllActiveLoanForReport(lineId);
		var dailyCollection = dailyCollectionRepository.getNotPaidUser(lineId, startDate, endDate);
		Map<Loan, List<Dailycollection>> dailyCollectionList = dailyCollection.stream()
				.collect(Collectors.groupingBy(Dailycollection::getLoan));
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				var list = dailyCollectionList.get(loanData.get(i));
				if (list != null && list.size() == number) {
					ClosedPartyResponse data = new ClosedPartyResponse();
					data.setLoanNo(loanData.get(i).getLoanNo());
					data.setName(loanData.get(i).getName());
					data.setAddress(loanData.get(i).getAddress());
					data.setLoanAmount(loanData.get(i).getLoanAmount());
					data.setDate(loanData.get(i).getCurrentLoanDate().toString());
					response.add(data);
				}
			}
		}
		return response;
	}

	public List<LedgerResponse> getLoanForLedger(String lineId, String startDate, String endDate) {
		var response = new ArrayList<LedgerResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startParsedDate = LocalDate.parse(startDate, formatters);
		LocalDate endParsedDate = LocalDate.parse(endDate, formatters);
		var loanData = loneRepository.getAllActiveLoanForReport(lineId);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				LedgerResponse ledgerResponse = new LedgerResponse();
				ledgerResponse.setLoanNo(loanData.get(i).getLoanNo());
				ledgerResponse.setName(loanData.get(i).getName());
				ledgerResponse.setAddress(loanData.get(i).getAddress());
				ledgerResponse.setBalance(loanData.get(i).getBalance());
				var dailyCollection = dailyCollectionRepository.getAmountPaidForParticularDateRange(lineId,
						startParsedDate, endParsedDate, loanData.get(i).getLoanId());
				var dateList = new ArrayList<DateValue>();
				if (!dailyCollection.isEmpty()) {
					for (int j = 0; j < dailyCollection.size(); j++) {
						DateValue dateValue = new DateValue();
						dateValue.setDate(dailyCollection.get(j).getDate());
						dateValue.setAmount(dailyCollection.get(j).getAmountPaid());
						dateList.add(dateValue);
					}
				}
				ledgerResponse.setDateValue(dateList);
				int sum = dailyCollection.stream().mapToInt(Dailycollection::getAmountPaid).sum();
				ledgerResponse.setTotal(sum);
				ledgerResponse.setPreBalance(loanData.get(i).getBalance() + sum);
				response.add(ledgerResponse);
			}
		}
		return response;
	}

	public List<NipResponse> getNipParty(String lineId, int number) {
		var response = new ArrayList<NipResponse>();
		LocalDate startDate = LocalDate.now().minusDays(number);
		var loanData = loneRepository.getNipLoanList(lineId, startDate);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				NipResponse nipResponse = new NipResponse();
				nipResponse.setLoanNo(loanData.get(i).getLoanNo());
				nipResponse.setName(loanData.get(i).getName());
				nipResponse.setAddress(loanData.get(i).getAddress());
				nipResponse.setLoanDate(loanData.get(i).getCurrentLoanDate().toString());
				nipResponse.setCloseDate(loanData.get(i).getAppxLoanClosedDate().toString());
				nipResponse.setLoanAmount(loanData.get(i).getLoanAmount());
				nipResponse.setPaidAmount(loanData.get(i).getLoanAmount() - loanData.get(i).getBalance());
				nipResponse.setBalance(loanData.get(i).getBalance());
				var days = ChronoUnit.DAYS.between(loanData.get(i).getCurrentLoanDate(), LocalDate.now());
				nipResponse.setPayDayss(days);
				response.add(nipResponse);
			}
		}
		return response;
	}

	public List<BulkPaidResponse> getBulkPaidLoan(String lineId, String startDate, String endDate) {
		var response = new ArrayList<BulkPaidResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startParsedDate = LocalDate.parse(startDate, formatters);
		LocalDate endParsedDate = LocalDate.parse(endDate, formatters);
		var loanData = loneRepository.getClosedLoanForParticularDateRange(lineId, startParsedDate, endParsedDate);
		if (loanData != null &&  !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				var dailyCollection = dailyCollectionRepository.getAllByLoanIdInDesc(loanData.get(i).getLoanId());
				if (!dailyCollection.isEmpty()
						&& dailyCollection.get(0).getAmountPaid() >= loanData.get(i).getLoanAmount() / 2) {
					BulkPaidResponse bulkPaidResponse = new BulkPaidResponse();
					bulkPaidResponse.setLoanNo(loanData.get(i).getLoanNo());
					bulkPaidResponse.setName(loanData.get(i).getName());
					bulkPaidResponse.setAddress(loanData.get(i).getAddress());
					bulkPaidResponse.setLoanAmount(loanData.get(i).getLoanAmount());
					bulkPaidResponse.setBillAmount(dailyCollection.get(0).getAmountPaid());
					bulkPaidResponse.setDate(loanData.get(i).getLoanClosedDate().toString());
					response.add(bulkPaidResponse);
				}
			}
		}
		return response;
	}

	public TotalLedgerResponse getLoanForLedger(String lineId) {
		TotalLedgerResponse totalLedgerResponse = new TotalLedgerResponse();
		var loanList = new ArrayList<NipResponse>();
		var loanReponse = loneRepository.getAllActiveLoanForReport(lineId);
		if (loanReponse != null && !loanReponse.isEmpty()) {
			totalLedgerResponse.setLoanCount(loanReponse.size());
			for (int i = 0; i < loanReponse.size(); i++) {
				NipResponse nipResponse = new NipResponse();
				nipResponse.setLoanNo(loanReponse.get(i).getLoanNo());
				nipResponse.setName(loanReponse.get(i).getName());
				nipResponse.setAddress(loanReponse.get(i).getAddress());
				nipResponse.setLoanDate(loanReponse.get(i).getCurrentLoanDate().toString());
				nipResponse.setCloseDate(loanReponse.get(i).getAppxLoanClosedDate().toString());
				nipResponse.setLoanAmount(loanReponse.get(i).getLoanAmount());
				nipResponse.setPaidAmount(loanReponse.get(i).getLoanAmount() - loanReponse.get(i).getBalance());
				nipResponse.setBalance(loanReponse.get(i).getBalance());
				loanList.add(nipResponse);
			}
			totalLedgerResponse.setLoanData(loanList);
		}
		return totalLedgerResponse;
	}

	public TotalLedgerResponse getLoanForLedgerForDateRange(String lineId, String dateRange) {
		TotalLedgerResponse totalLedgerResponse = new TotalLedgerResponse();
		var loanList = new ArrayList<NipResponse>();
		LocalDate startParsedDate = null;
		LocalDate endParsedDate = null;
		List<Loan> loanReponse = null;
		if (dateRange.equalsIgnoreCase("below120")) {
			startParsedDate = LocalDate.now().minusDays(2);
			endParsedDate = LocalDate.now();
			loanReponse = loneRepository.getLoanForLedger(lineId, startParsedDate, endParsedDate);
		} else if (dateRange.equalsIgnoreCase("120to240")) {
			startParsedDate = LocalDate.now().minusDays(4);
			endParsedDate = LocalDate.now().minusDays(2);
			loanReponse = loneRepository.getLoanForLedger(lineId, startParsedDate, endParsedDate);
		} else if (dateRange.equalsIgnoreCase("240to365")) {
			startParsedDate = LocalDate.now().minusDays(6);
			endParsedDate = LocalDate.now().minusDays(4);
			loanReponse = loneRepository.getLoanForLedger(lineId, startParsedDate, endParsedDate);
		} else if (dateRange.equalsIgnoreCase("above365")) {
			startParsedDate = LocalDate.now().minusDays(6);
			loanReponse = loneRepository.getNipLoanList(lineId, startParsedDate);
		}
		if (loanReponse != null && !loanReponse.isEmpty()) {
			totalLedgerResponse.setLoanCount(loanReponse.size());
			for (int i = 0; i < loanReponse.size(); i++) {
				NipResponse nipResponse = new NipResponse();
				nipResponse.setLoanNo(loanReponse.get(i).getLoanNo());
				nipResponse.setName(loanReponse.get(i).getName());
				nipResponse.setAddress(loanReponse.get(i).getAddress());
				nipResponse.setLoanDate(loanReponse.get(i).getCurrentLoanDate().toString());
				nipResponse.setCloseDate(loanReponse.get(i).getAppxLoanClosedDate().toString());
				nipResponse.setLoanAmount(loanReponse.get(i).getLoanAmount());
				nipResponse.setPaidAmount(loanReponse.get(i).getLoanAmount() - loanReponse.get(i).getBalance());
				nipResponse.setBalance(loanReponse.get(i).getBalance());
				loanList.add(nipResponse);
			}
			totalLedgerResponse.setLoanData(loanList);
		}
		return totalLedgerResponse;
	}

	public MonthlyLoanResponse getMonthlyLoan(String lineId, String startDate, String endDate) {
		MonthlyLoanResponse monthlyLoanResponse = new MonthlyLoanResponse();
		var list = new ArrayList<ClosedPartyResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startParsedDate = LocalDate.parse(startDate, formatters);
		LocalDate endParsedDate = LocalDate.parse(endDate, formatters);
		var loanData = loneRepository.getActiveLoanForParticularDateRange(lineId, startParsedDate, endParsedDate);
		if (loanData != null && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				ClosedPartyResponse data = new ClosedPartyResponse();
				data.setLoanNo(loanData.get(i).getLoanNo());
				data.setName(loanData.get(i).getName());
				data.setDate(loanData.get(i).getCurrentLoanDate().toString());
				data.setLoanAmount(loanData.get(i).getLoanAmount());
				data.setInterest(loanData.get(i).getInterest());
				list.add(data);
			}
			int loanAmountTotal = loanData.stream().mapToInt(Loan::getLoanAmount).sum();
			int interestTotal = loanData.stream().mapToInt(Loan::getCommissionAmount).sum();
			monthlyLoanResponse.setLoanData(list);
			monthlyLoanResponse.setLoanAmountTotal(loanAmountTotal);
			monthlyLoanResponse.setInterestTotal(interestTotal);
		}
		return monthlyLoanResponse;
	}
}
