package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.OutstandingBalance;
import com.java.finance.ThirukumaranFinance.Domain.TotalBalance;
import com.java.finance.ThirukumaranFinance.Entity.DailyTotal;
import com.java.finance.ThirukumaranFinance.Entity.DateClose;
import com.java.finance.ThirukumaranFinance.Entity.OutStandingBalance;
import com.java.finance.ThirukumaranFinance.Entity.ThittamData;
import com.java.finance.ThirukumaranFinance.Repository.DailyCollectionRepository;
import com.java.finance.ThirukumaranFinance.Repository.DailyTotalRepository;
import com.java.finance.ThirukumaranFinance.Repository.DateCloseRepository;
import com.java.finance.ThirukumaranFinance.Repository.LineRepository;
import com.java.finance.ThirukumaranFinance.Repository.LoneRepository;
import com.java.finance.ThirukumaranFinance.Repository.OutStandingBalanceRepository;
import com.java.finance.ThirukumaranFinance.Repository.ThittamDataRepository;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateCloseService {

	private final DateCloseRepository dateCloseRepository;

	private final DailyCollectionRepository dailyCollectionRepository;

	private final DailyTotalRepository dailyTotalRepository;

	private final LineRepository lineRepository;

	private final LoneRepository loneRepository;

	private final ThittamDataRepository thittamDataRepository;
	
	private final OutStandingBalanceRepository outStandingBalanceRepository;

	public GenericResponse createDateClose(DateCloseRequest dateCloseRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parsedDate = LocalDate.parse(dateCloseRequest.getDate(), formatter);
			var date = dateCloseRepository.findByDate(dateCloseRequest.getLineId(), parsedDate);
			var dateClose = new DateClose();
			dateClose.setCreatedOn(LocalDateTime.now());
			dateClose.setLineId(dateCloseRequest.getLineId());
			dateClose.setDate(parsedDate);
			dateCloseRepository.save(dateClose);
			if (date == null) {
				var lineData = lineRepository.findByLineId(dateClose.getLineId());
				updateDailyTotal(dateClose.getLineId(), dateClose.getDate());
				updateLoanTable(dateClose.getLineId());
				updateThittamData(dateClose.getLineId(), dateClose.getDate(),lineData.getLineName());
				updateOutStandingBalance(dateClose.getLineId(), dateClose.getDate(),lineData.getLineName());
			}
			genericResponse.setMessage("Date close  created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create dateClose");
			return genericResponse;
		}
	}

	private void updateOutStandingBalance(String lineId, LocalDate date, String lineName) {
		try {
		var firstResponse = getFirstOutstandingBalance(lineId, date);
		var secondResponse = getSecondOutstandingBalance(lineId, date);
		var thirdResponse = getThirdOutstandingBalance(lineId, date);
		var fourthResponse = getFourthOutstandingBalance(lineId, date);
		var entity = new OutStandingBalance();
		entity.setDate(date);
		entity.setLineName(lineName);
		entity.setFirstCount(firstResponse.getCount());
		entity.setFirstBalance(firstResponse.getBalance());
		entity.setSecondCount(secondResponse.getCount());
		entity.setSecondBalance(secondResponse.getBalance());
		entity.setThirdCount(thirdResponse.getCount());
		entity.setThirdBalance(thirdResponse.getBalance());
		entity.setFourthCount(fourthResponse.getCount());
		entity.setFourthBalance(fourthResponse.getBalance());
		outStandingBalanceRepository.save(entity);
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
		}

	}

	private OutstandingBalance getFourthOutstandingBalance(String lineId, LocalDate date) {
		var startParsedDate = date.minusDays(6);
		var tuple = loneRepository.getOutstandingBalanceForParticularDate(lineId, startParsedDate);
		return getOutstandingBalance(tuple);
	}

	private OutstandingBalance getThirdOutstandingBalance(String lineId, LocalDate date) {
		var startParsedDate = date.minusDays(6);
		var endParsedDate = date.minusDays(4);
		var tuple = loneRepository.getOutstandingBalanceForDateRange(lineId, startParsedDate, endParsedDate);
		return getOutstandingBalance(tuple);
	}

	private OutstandingBalance getOutstandingBalance(List<Tuple> tuplelist) {
		OutstandingBalance outstandingBalance = new OutstandingBalance();
		if(!tuplelist.isEmpty()) {
			Tuple tuple = tuplelist.get(0);		
			outstandingBalance.setBalance(tocheckNull(tuple.get("total_balance", Long.class)));
			outstandingBalance.setCount(tocheckNull(tuple.get("loan_count", Long.class)));
		}
		return outstandingBalance;
	}
	
	public int tocheckNull(Object value) {
		return Objects.isNull(value) ? 0 : ((Long) value).intValue();
	}

	private OutstandingBalance getSecondOutstandingBalance(String lineId, LocalDate date) {
		var startParsedDate = date.minusDays(4);
		var endParsedDate = date.minusDays(2);
		var tuple = loneRepository.getOutstandingBalanceForDateRange(lineId, startParsedDate, endParsedDate);
		return getOutstandingBalance(tuple);
	}

	private OutstandingBalance getFirstOutstandingBalance(String lineId, LocalDate date) {
		var startParsedDate = date.minusDays(2);
		var tuple = loneRepository.getOutstandingBalanceForDateRange(lineId, startParsedDate, date);
		return getOutstandingBalance(tuple);
	}

	private void updateThittamData(String lineId, LocalDate date, String lineName) {
		try {
			var loanData = loneRepository.getTotalBalanceForThittam(lineId, date);
			TotalBalance totalBalance;
			if(!loanData.isEmpty()) {
				totalBalance = balanceBuilder(loanData);
			getBillAmount(lineId, date, lineName);
			getLoanAmount(totalBalance.getLoanTotal(), date, lineName);
			getCommisionAmount(totalBalance.getCommissionTotal(), date, lineName);
			getSeetutAmount(totalBalance.getSeetuTotal(), date, lineName);
			getExcessAmount(lineId, date, lineName);
			}
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
		}
	}

	private TotalBalance balanceBuilder(List<Tuple> loanData) {
		TotalBalance totalBalance = new TotalBalance();
		Tuple tuple = loanData.get(0);
		totalBalance.setLoanTotal(tocheckNull(tuple.get("loanTotal", Long.class)));
		totalBalance.setCommissionTotal(tocheckNull(tuple.get("commissionTotal", Long.class)));
		totalBalance.setSeetuTotal(tocheckNull(tuple.get("seetuTotal", Long.class)));
		totalBalance.setExcessTotal(tocheckNull(tuple.get("excessTotal", Long.class)));
		return totalBalance;
	}

	private void getExcessAmount(String lineId,LocalDate date, String lineName) {
		var excessAmount = loneRepository.getExcessAmountForDate(lineId, date);
		var excessTotal = getExcessAmount(excessAmount);
		var entity = new ThittamData();
		entity.setName(lineName + " " +"EXCESS");
		entity.setCredit(excessTotal);
		entity.setDate(date);
		entity.setExtraHead(false);
		thittamDataRepository.save(entity);
	}
	
	private int getExcessAmount(List<Tuple> tuplelist) {
		int excess = 0;
		if(!tuplelist.isEmpty()) {
			Tuple tuple = tuplelist.get(0);		
			 excess = tocheckNull(tuple.get("excessTotal", Long.class));
		}
		return excess;
	}

	private void getSeetutAmount(int seetuTotal, LocalDate date, String lineName) {
		var entity = new ThittamData();
		entity.setName(lineName + " " +"SEETU");
		entity.setCredit(seetuTotal);
		entity.setDate(date);
		entity.setExtraHead(false);
		thittamDataRepository.save(entity);
	}

	private void getCommisionAmount(int commissionTotal, LocalDate date, String lineName) {
		var entity = new ThittamData();
		entity.setName(lineName + " " +"COMMISSION");
		entity.setCredit(commissionTotal);
		entity.setDate(date);
		entity.setExtraHead(false);
		thittamDataRepository.save(entity);
	}

	private void getLoanAmount(int loanTotal, LocalDate date, String lineName) {
		var entity = new ThittamData();
		entity.setName(lineName + " " +"LOAN");
		entity.setDebit(loanTotal);
		entity.setDate(date);
		entity.setExtraHead(false);
		thittamDataRepository.save(entity);
	}

	private void getBillAmount(String lineId, LocalDate date, String lineName) {
		var billAmount = dailyCollectionRepository.getTotalAmountForAllCollection(lineId, date);
		if(billAmount != null) {
		var entity = new ThittamData();
		entity.setName(lineName + " "+ "BILL");
		entity.setCredit(billAmount);
		entity.setDate(date);
		entity.setExtraHead(false);
		thittamDataRepository.save(entity);
		}
	}

	private void updateLoanTable(String lineId) {
		try {
			var loanDto = loneRepository.getAllActiveLoanForReport(lineId);
			if (!loanDto.isEmpty()) {
				for (int i = 0; i < loanDto.size(); i++) {
					loanDto.get(i).setDailyUpdate(false);
				}
				loneRepository.saveAll(loanDto);
			}
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
		}

	}

	private void updateDailyTotal(String lineId, LocalDate date) {
		var response = dailyCollectionRepository.getTotalAmountForAllCollection(lineId, date);
		if (response != null && response >= 0) {
			var data = new DailyTotal();
			data.setLineId(lineId);
			data.setTotalAmount(response);
			data.setDate(date);
			dailyTotalRepository.save(data);
		}

	}

	public List<DateClose> getAllDateClose(String lineId) {
		var entity = dateCloseRepository.findByLineId(lineId);
		return entity;
	}

}
