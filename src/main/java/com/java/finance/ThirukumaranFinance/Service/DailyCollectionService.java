package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.BillEntryResponse;
import com.java.finance.ThirukumaranFinance.Domain.DailyCollectionData;
import com.java.finance.ThirukumaranFinance.Domain.DailyCollectionRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateValue;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.LedgerResponse;
import com.java.finance.ThirukumaranFinance.Domain.UpdateDailyCollectionRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateDailyCollectionResponse;
import com.java.finance.ThirukumaranFinance.Entity.Dailycollection;
import com.java.finance.ThirukumaranFinance.Repository.DailyCollectionRepository;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyCollectionService {

	private final LoneRepository loanRepository;

	private final DailyCollectionRepository dailyCollectionRepository;

	public List<DailyCollectionData> getAllActiveLoan(String lineId) {
		var response = new ArrayList<DailyCollectionData>();
		var loanData = loanRepository.getAllActiveLoan(lineId);
		if (loanData.size() > 0 && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				DailyCollectionData dailyCollectionData = new DailyCollectionData();
				dailyCollectionData.setLoanNo(loanData.get(i).getLoanNo());
				dailyCollectionData.setName(loanData.get(i).getName());
				dailyCollectionData.setAddress(loanData.get(i).getAddress());
				dailyCollectionData.setBalance(loanData.get(i).getBalance());
				var payamount = (loanData.get(i).getLoanAmount()) / 100;
				dailyCollectionData.setPayAmount(String.valueOf(payamount));
				response.add(dailyCollectionData);
			}
		}
		return response;
	}

	public List<LedgerResponse> getAllActiveLoanLedger(String lineId) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		var formattedDate = localDate.format(formatter);
		LocalDate date = LocalDate.parse(formattedDate, formatter);
		LocalDate belowTenDaysDate = date.minusDays(11);
		var response = new ArrayList<LedgerResponse>();
		var loanData = loanRepository.getAllActiveLoan(lineId);
		if (loanData.size() > 0 && !loanData.isEmpty()) {
			for (int i = 0; i < loanData.size(); i++) {
				LedgerResponse dailyCollectionData = new LedgerResponse();
				dailyCollectionData.setLoanNo(loanData.get(i).getLoanNo());
				dailyCollectionData.setName(loanData.get(i).getName());
				dailyCollectionData.setAddress(loanData.get(i).getAddress());
				dailyCollectionData.setBalance(loanData.get(i).getBalance());
				var payamount = (loanData.get(i).getLoanAmount()) / 100;
				dailyCollectionData.setPayAmount(String.valueOf(payamount));
				var dailyCollection = dailyCollectionRepository.getAmountPaidForParticularDateRange(lineId, belowTenDaysDate, localDate, loanData.get(i).getLoanId());
				var dateList = new ArrayList<DateValue>();
				if (!dailyCollection.isEmpty()) {
					for (int j = 0; j < dailyCollection.size(); j++) {
						DateValue dateValue = new DateValue();
						dateValue.setDate(dailyCollection.get(j).getDate());
						dateValue.setAmount(dailyCollection.get(j).getAmountPaid());
						dateList.add(dateValue);
					}
				}
				dailyCollectionData.setDateValue(dateList);
				response.add(dailyCollectionData);
			}
		}
		return response;
	}

	public GenericResponse createDailyCollection(DailyCollectionRequest request) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			var formattedDate = localDate.format(formatter);
			LocalDate date = LocalDate.parse(formattedDate, formatter);
			var loanData = loanRepository.findByLoanNoAndLineId(request.getLineId(), request.getLoanNo());
			if (loanData != null) {
				int balance = loanData.getBalance() - Integer.parseInt(request.getAmountPaid());
				loanData.setDailyUpdate(true);
				loanData.setBalance(balance);
				if (balance == 0) {
					loanData.setLoanClosedDate(date);
					loanData.setLoanActive(false);
				} else if (balance < 0) {
					var excessAmount = Math.abs(balance);
					loanData.setBalance(0);
					loanData.setLoanClosedDate(date);
					loanData.setLoanActive(false);
					loanData.setExcessAmount(excessAmount);
				}
				loanRepository.save(loanData);
				var dailyCollection = new Dailycollection();
				dailyCollection.setAmountPaid(Integer.parseInt(request.getAmountPaid()));
				LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
				dailyCollection.setDate(parsedDate);
				dailyCollection.setCreatedOn(LocalDateTime.now());
				dailyCollection.setUpdatedOn(LocalDateTime.now());
				dailyCollection.setLoan(loanData);
				dailyCollection.setLineId(request.getLineId());
				dailyCollectionRepository.save(dailyCollection);
			}
			genericResponse.setMessage("Daily Collection created successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create Daily Collection");
			return genericResponse;
		}
	}

	public List<BillEntryResponse> getAllDailyCollection(DateCloseRequest request) {
		var response = new ArrayList<BillEntryResponse>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(request.getDate(), formatters);
		var dailyCollection = dailyCollectionRepository.getAllDailyCollection(request.getLineId(), parsedDate);
		if (!dailyCollection.isEmpty()) {
			for (int i = 0; i < dailyCollection.size(); i++) {
				BillEntryResponse data = new BillEntryResponse();
				data.setLoanNo(dailyCollection.get(i).getLoan().getLoanNo());
				data.setName(dailyCollection.get(i).getLoan().getName());
				data.setBillAmount(dailyCollection.get(i).getAmountPaid());
				data.setExcess(dailyCollection.get(i).getLoan().getExcessAmount());
				data.setTime(dailyCollection.get(i).getUpdatedOn().toLocalTime().toString());
				response.add(data);
			}
		}
		return response;
	}

	public GenericResponse updateDailyCollection(UpdateDailyCollectionRequest request) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			var formattedDate = localDate.format(formatter);
			LocalDate date = LocalDate.parse(formattedDate, formatter);
			var loanData = loanRepository.findByLoanNoAndLineId(request.getLineId(), request.getLoanNo());
			if (loanData != null) {
				var balance = (loanData.getBalance()) + Integer.parseInt(request.getOldAmount());
				balance = balance - Integer.parseInt(request.getNewAmountPaid());
				if (loanData.getBalance() == 0) {
					if (balance == 0) {
						loanData.setBalance(0);
						loanData.setLoanClosedDate(date);
						loanData.setLoanActive(false);
						loanData.setDailyUpdate(true);
					} else if (balance < 0) {
						var excessAmount = Math.abs(balance);
						loanData.setBalance(0);
						loanData.setLoanClosedDate(date);
						loanData.setLoanActive(false);
						loanData.setExcessAmount(excessAmount);
						loanData.setDailyUpdate(true);
					} else {
						loanData.setLoanClosedDate(null);
						loanData.setLoanActive(true);
						loanData.setExcessAmount(0);
						loanData.setBalance(balance);
						loanData.setDailyUpdate(true);
					}
				} else {
					if (balance == 0) {
						loanData.setBalance(0);
						loanData.setLoanClosedDate(date);
						loanData.setLoanActive(false);
						loanData.setDailyUpdate(true);
					} else if (balance < 0) {
						var excessAmount = Math.abs(balance);
						loanData.setBalance(0);
						loanData.setLoanClosedDate(date);
						loanData.setLoanActive(false);
						loanData.setExcessAmount(excessAmount);
						loanData.setDailyUpdate(true);
					} else {
						loanData.setDailyUpdate(true);
						loanData.setBalance(balance);
					}
				}

				loanRepository.save(loanData);
				LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
				var dailyCollection = dailyCollectionRepository.getByLoanId(loanData.getLoanId(), parsedDate);
				if (dailyCollection != null) {
					dailyCollection.setAmountPaid(Integer.parseInt(request.getNewAmountPaid()));
					dailyCollection.setUpdatedOn(LocalDateTime.now());
					dailyCollectionRepository.save(dailyCollection);
				}
			}
			genericResponse.setMessage("Daily collection updated successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("failed to update daily collection");
			return genericResponse;
		}
	}

	public UpdateDailyCollectionResponse getparticularCollection(DailyCollectionRequest request) {
		UpdateDailyCollectionResponse response = new UpdateDailyCollectionResponse();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
		var loanData = loanRepository.findByLoanNoAndLineId(request.getLineId(), request.getLoanNo());
		var dailyCollection = dailyCollectionRepository.getByLoanId(loanData.getLoanId(), parsedDate);
		if (dailyCollection != null) {
			response.setAmountPaid(String.valueOf(dailyCollection.getAmountPaid()));
		}
		return response;
	}

}
