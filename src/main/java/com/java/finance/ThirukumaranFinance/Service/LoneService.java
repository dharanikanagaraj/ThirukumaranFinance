package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.LoanRequest;
import com.java.finance.ThirukumaranFinance.Entity.Loan;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoneService {

	private final LoneRepository loneRepository;

	public String createLone(LoanRequest loneRequest) {
		try {
			var loanDto = new Loan();
			loanDto.setLoanNo(loneRequest.getLoanNo());
			loanDto.setUserNo(loneRequest.getUserNo());
			loanDto.setName(loneRequest.getName());
			loanDto.setAddress(loneRequest.getAddress());
			loanDto.setPhoneNo(loneRequest.getPhoneNo());
			loanDto.setOrderNo(loneRequest.getOrderNo());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate parsedDate = LocalDate.parse(loneRequest.getCurrentDate(), formatter);
			loanDto.setCurrentLoanDate(parsedDate);
			loanDto.setLoanAmount(loneRequest.getLoanAmount());
			loanDto.setSeetuAmount(loneRequest.getSeetuAmount());
			loanDto.setExcessAmount("0");
			loanDto.setCommissionAmount(loneRequest.getCommissionAmount());
			loanDto.setInterest(loneRequest.getInterest());
			loanDto.setLineId(loneRequest.getLineId());
			loanDto.setAppxLoanClosedDate(parsedDate.plusDays(100));
			loanDto.setBalance(loneRequest.getLoanAmount());
			loanDto.setLoanActive(true);
			loanDto.setDailyUpdate(false);
			loneRepository.save(loanDto);
			return "Loan Created Successfully";
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			return "Failed to create loan";
		}
	}

	public String updateLone(LoanRequest loneRequest) {
		try {
			var loanDto = loneRepository.findByLoanNoAndLineId(loneRequest.getLineId(), loneRequest.getLoanNo());
			if (!loanDto.getLoanAmount().equals(loneRequest.getLoanAmount())) {
				var addedLoanAmount = (Integer.parseInt(loneRequest.getLoanAmount()))
						- (Integer.parseInt(loanDto.getLoanAmount()));
				loanDto.setBalance(String.valueOf((Integer.parseInt(loanDto.getBalance())) + (addedLoanAmount)));
			}
			loanDto.setName(loneRequest.getName());
			loanDto.setAddress(loneRequest.getAddress());
			loanDto.setPhoneNo(loneRequest.getPhoneNo());
			loanDto.setOrderNo(loneRequest.getOrderNo());
			loanDto.setLoanAmount(loneRequest.getLoanAmount());
			loanDto.setSeetuAmount(loneRequest.getSeetuAmount());
			loanDto.setCommissionAmount(loneRequest.getCommissionAmount());
			loanDto.setInterest(loneRequest.getInterest());
			loneRepository.save(loanDto);
			return "Loan Updated Successfully";
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			return "Failed to update loan";
		}
	}

	public List<Loan> getAllTodayLoan(String lineId, String date) {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate parsedDate = LocalDate.parse(date, formatters);
		var response = loneRepository.findAllLoanForPresentDay(lineId, parsedDate);
		return response;
	}

	public String deleteLoan(String lineId, String loanNo) {
		try {
			var loanDto = loneRepository.findByLoanNoAndLineId(lineId, loanNo);
			loneRepository.delete(loanDto);
			return "Loan deleted Successfully";
		} catch (Exception e) {
			return "Failed to delete Loan";
		}
	}

	public Loan getParticularLoan(String lineId, String loanNo) {
		return loneRepository.findByLoanNoAndLineId(lineId, loanNo);

	}
}
