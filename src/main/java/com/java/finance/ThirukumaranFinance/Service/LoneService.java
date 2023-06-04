package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.LoanRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateLoanRequest;
import com.java.finance.ThirukumaranFinance.Entity.Loan;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoneService {

	private final LoneRepository loneRepository;

	public GenericResponse createLone(LoanRequest loneRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var loanDto = new Loan();
			loanDto.setLoanNo(getLatestSequenceForLoan(loneRequest.getLineId()));
			if (loneRequest.getUserNo() != null) {
				loanDto.setUserNo(loneRequest.getUserNo());
			} else {
				loanDto.setUserNo(getLatestSequenceForUser(loneRequest.getLineId()));
			}
			loanDto.setName(loneRequest.getName());
			loanDto.setAddress(loneRequest.getAddress());
			loanDto.setPhoneNo(loneRequest.getPhoneNo());
			loanDto.setOrderNo(loneRequest.getOrderNo());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate parsedDate = LocalDate.parse(loneRequest.getCurrentDate(), formatter);
			loanDto.setCurrentLoanDate(parsedDate);
			loanDto.setLoanAmount(Integer.parseInt(loneRequest.getLoanAmount()));
			loanDto.setSeetuAmount(Integer.parseInt(loneRequest.getSeetuAmount()));
			loanDto.setExcessAmount(0);
			loanDto.setCommissionAmount(Integer.parseInt(loneRequest.getCommissionAmount()));
			loanDto.setInterest(Integer.parseInt(loneRequest.getInterest()));
			loanDto.setLineId(loneRequest.getLineId());
			loanDto.setAppxLoanClosedDate(parsedDate.plusDays(100));
			loanDto.setBalance(Integer.parseInt(loneRequest.getLoanAmount()));
			loanDto.setLoanActive(true);
			loanDto.setDailyUpdate(false);
			loneRepository.save(loanDto);
			genericResponse.setMessage("Loan Created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create loan");
			return genericResponse;
		}
	}

	public GenericResponse updateLone(UpdateLoanRequest loneRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var loanDto = loneRepository.findByLoanNoAndLineId(loneRequest.getLineId(), loneRequest.getLoanNo());
			if (loanDto.getLoanAmount() != Integer.parseInt((loneRequest.getLoanAmount()))) {
				var addedLoanAmount = (Integer.parseInt(loneRequest.getLoanAmount())) - (loanDto.getLoanAmount());
				loanDto.setBalance(loanDto.getBalance() + addedLoanAmount);
			}
			loanDto.setName(loneRequest.getName());
			loanDto.setAddress(loneRequest.getAddress());
			loanDto.setPhoneNo(loneRequest.getPhoneNo());
			loanDto.setOrderNo(loneRequest.getOrderNo());
			loanDto.setLoanAmount(Integer.parseInt(loneRequest.getLoanAmount()));
			loanDto.setSeetuAmount(Integer.parseInt(loneRequest.getSeetuAmount()));
			loanDto.setCommissionAmount(Integer.parseInt(loneRequest.getCommissionAmount()));
			loanDto.setInterest(Integer.parseInt(loneRequest.getInterest()));
			loneRepository.save(loanDto);
			genericResponse.setMessage("Loan Updated Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to update loan");
			return genericResponse;
		}
	}

	public List<Loan> getAllTodayLoan(String lineId, String date) {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var response = loneRepository.findAllLoanForPresentDay(lineId, parsedDate);
		return response;
	}

	public GenericResponse deleteLoan(String lineId, String loanNo) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var loanDto = loneRepository.findByLoanNoAndLineId(lineId, loanNo);
			loneRepository.delete(loanDto);
			genericResponse.setMessage("Loan deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			genericResponse.setMessage("Failed to delete Loan");
			return genericResponse;
		}
	}

	public Loan getParticularLoan(String lineId, String loanNo) {
		return loneRepository.findByLoanNoAndLineId(lineId, loanNo);

	}

	public String getLatestSequenceForLoan(String lineId) {
		String newValue;
		String originalValue = loneRepository.findSequenceForLoan(lineId);
		if (originalValue != null && !originalValue.isBlank()) {
			// Extract the numeric part of the string
			String numericPart = originalValue.substring(3);
			// Convert the numeric part to an integer and increment by one
			int incrementedValue = Integer.parseInt(numericPart) + 1;
			// Combine the original prefix with the incremented value
			newValue = originalValue.substring(0, 3) + String.format("%02d", incrementedValue);
			System.out.println("Incremented value: " + newValue);
		} else {
			newValue = "Lon01";
		}
		return newValue;
	}

	public String getLatestSequenceForUser(String lineId) {
		String newValue;
		String originalValue = loneRepository.findSequenceForUser(lineId);
		if (originalValue != null && !originalValue.isBlank()) {
			// Extract the numeric part of the string
			String numericPart = originalValue.substring(2);
			// Convert the numeric part to an integer and increment by one
			int incrementedValue = Integer.parseInt(numericPart) + 1;
			// Combine the original prefix with the incremented value
			newValue = originalValue.substring(0, 2) + String.format("%02d", incrementedValue);
			System.out.println("Incremented value: " + newValue);
		} else {
			newValue = "Us01";
		}
		return newValue;
	}
}
