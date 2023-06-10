package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.BalanceData;
import com.java.finance.ThirukumaranFinance.Domain.BalanceRequest;
import com.java.finance.ThirukumaranFinance.Domain.DateRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.HeadDataRequest;
import com.java.finance.ThirukumaranFinance.Domain.IndividualHeadRequest;
import com.java.finance.ThirukumaranFinance.Domain.ThittamDateRequest;
import com.java.finance.ThirukumaranFinance.Domain.ThittamResponse;
import com.java.finance.ThirukumaranFinance.Entity.Head;
import com.java.finance.ThirukumaranFinance.Entity.OutStandingBalance;
import com.java.finance.ThirukumaranFinance.Entity.ThittamData;
import com.java.finance.ThirukumaranFinance.Repository.HeadRepository;
import com.java.finance.ThirukumaranFinance.Repository.OutStandingBalanceRepository;
import com.java.finance.ThirukumaranFinance.Repository.ThittamDataRepository;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThittamService {

	private final HeadRepository headRepository;
	private final ThittamDataRepository thittamDataRepository;
	private final OutStandingBalanceRepository outStandingBalanceRepository;

	public GenericResponse createUpdateHead(int id, String name, boolean update, boolean extraHead) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (update) {
				var entity = headRepository.findById(id);
				entity.setHeadName(name);
				entity.setExtraHead(extraHead);
				headRepository.save(entity);
				genericResponse.setMessage("Head updated successfully");
			} else {
				var entity = new Head();
				entity.setHeadName(name);
				entity.setExtraHead(extraHead);
				headRepository.save(entity);
				genericResponse.setMessage("Head created successfully");
			}
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed for Head");
			return genericResponse;
		}
	}

	public GenericResponse deleteHead(int id) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = headRepository.findById(id);
			headRepository.delete(entity);
			genericResponse.setMessage("Head deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to delete Head");
			return genericResponse;
		}
	}

	public List<Head> getAllHead() {
		var entity = headRepository.findAll();
		return entity;
	}

	public GenericResponse createHeadData(HeadDataRequest request) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = new ThittamData();
			entity.setName(request.getName());
			entity.setDescription(request.getDescription());
			entity.setCredit(request.getCredit());
			entity.setDebit(request.getDebit());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
			entity.setDate(parsedDate);
			entity.setExtraHead(true);
			thittamDataRepository.save(entity);
			genericResponse.setMessage("Head Data created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create head Data");
			return genericResponse;
		}
	}

	public List<HeadDataRequest> getThittamDataForExtraHead(ThittamDateRequest request) {
		var response = new ArrayList<HeadDataRequest>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(request.getDate(), formatter);
		var data = thittamDataRepository.findThittamDataforExtraHead(date);
		if (!data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				HeadDataRequest headDataRequest = new HeadDataRequest();
				headDataRequest.setName(data.get(i).getName());
				headDataRequest.setDescription(data.get(i).getDescription());
				headDataRequest.setCredit(data.get(i).getCredit());
				headDataRequest.setDebit(data.get(i).getDebit());
				headDataRequest.setDate(data.get(i).getDate().toString());
				response.add(headDataRequest);
			}
		}
		return response;
	}

	public GenericResponse deleteHeadData(String name, String date) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parsedDate = LocalDate.parse(date, formatter);
			var entity = thittamDataRepository.findByNameAndDate(name, parsedDate);
			thittamDataRepository.deleteAll(entity);
			genericResponse.setMessage("Head Data deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to delete Head");
			return genericResponse;
		}
	}

	public GenericResponse createBalance(BalanceRequest request) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = new ThittamData();
			entity.setName(request.getName());
			entity.setBalance(request.getBalance());
			if (request.getName().equalsIgnoreCase("Closing Balance")) {
				entity.setExtraHead(true);
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
			entity.setDate(parsedDate);
			thittamDataRepository.save(entity);
			genericResponse.setMessage("Balance created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create balance");
			return genericResponse;
		}
	}

	public GenericResponse deleteAllExtraHeadForDate(String date) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parsedDate = LocalDate.parse(date, formatter);
			var data = thittamDataRepository.findThittamDataforExtraHead(parsedDate);
			thittamDataRepository.deleteAll(data);
			genericResponse.setMessage("Data deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to delete Data");
			return genericResponse;
		}
	}

	public ThittamResponse getAllThittamData(ThittamDateRequest request) {
		ThittamResponse thittamResponse = new ThittamResponse();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(request.getDate(), formatter);
		var entity = thittamDataRepository.findByNameAndDateForBalance("Balance", parsedDate.minusDays(1));
		if (entity != null) {
			thittamResponse.setOpeningBalance(entity.getBalance());
			thittamResponse.setDate(parsedDate.toString());
			thittamResponse.setPrevDate(parsedDate.minusDays(1).toString());
			var dataList = thittamDataRepository.findThittamData(parsedDate);
			if (!dataList.isEmpty()) {
				var headDataRequestList = new ArrayList<HeadDataRequest>();
				for (int i = 0; i < dataList.size(); i++) {
					HeadDataRequest headDataRequest = new HeadDataRequest();
					headDataRequest.setName(dataList.get(i).getName());
					headDataRequest.setDescription(dataList.get(i).getDescription());
					headDataRequest.setDebit(dataList.get(i).getDebit());
					headDataRequest.setCredit(dataList.get(i).getCredit());
					headDataRequestList.add(headDataRequest);
				}
				thittamResponse.setThittamList(headDataRequestList);
			}
		}
		return thittamResponse;
	}

	public List<OutStandingBalance> getAllOutStandingBalance(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(date, formatter);
		var response = outStandingBalanceRepository.getOutStandingBalanceForDate(parsedDate);
		return response;
	}

	public List<ThittamData> getAllAccountData(DateRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(request.getStartDate(), formatter);
		LocalDate endDate = LocalDate.parse(request.getEndDate(), formatter);
		var response = thittamDataRepository.getAccountData(startDate, endDate);
		return response;
	}

	public List<ThittamData> getAllIndividualHeadData(IndividualHeadRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(request.getStartDate(), formatter);
		LocalDate endDate = LocalDate.parse(request.getEndDate(), formatter);
		var response = thittamDataRepository.getIndividualData(request.getName(), startDate, endDate);
		return response;
	}

	public List<BalanceData> getAlldataforBalanceSheet(DateRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(request.getStartDate(), formatter);
		LocalDate endDate = LocalDate.parse(request.getEndDate(), formatter);
		var balanceDataList = new ArrayList<BalanceData>();
		var entity = thittamDataRepository.findByNameAndDateForBalance("Balance", startDate.minusDays(1));
		var openingBalance = getOpeningBalance(entity);
		balanceDataList.add(openingBalance);
		var bill = thittamDataRepository.getBillDataWithDate(startDate, endDate);
		var billBalance = getDefaultColumnBalance(bill, "Vasul Varavu");
		balanceDataList.add(billBalance);
		var commission = thittamDataRepository.getCommissionDataWithDate(startDate, endDate);
		var commissonBalance = getDefaultColumnBalance(commission, "Commission");
		balanceDataList.add(commissonBalance);
		var loan = thittamDataRepository.getLoanDataWithDate(startDate, endDate);
		var loanBalance = getDefaultColumnBalance(loan, "Loan");
		balanceDataList.add(loanBalance);
		var seetu = thittamDataRepository.getSeetuDataWithDate(startDate, endDate);
		var seetuBalance = getDefaultColumnBalance(seetu, "Seetu");
		balanceDataList.add(seetuBalance);
		var excessBalance = thittamDataRepository.getExcessDataWithDate(startDate, endDate);
		var excess = getDefaultColumnBalance(excessBalance, "Excess");
		balanceDataList.add(excess);
		var tupleList = thittamDataRepository.getBalanceSheet(startDate, endDate);
		var allDatalist = getBalanceDataList(tupleList);
		for (int i = allDatalist.size() - 1; i >= 0; i--) {
			var data = allDatalist.get(i);
			if (data.getName().contains("LOAN") || data.getName().contains("BILL")
					|| data.getName().contains("COMMISSION") || data.getName().contains("SEETU")
					|| data.getName().contains("EXCESS") || data.getName().contains("Balance")) {
				allDatalist.remove(i);
			}
		}
		balanceDataList.addAll(allDatalist);
		return balanceDataList;
	}

	private BalanceData getOpeningBalance(ThittamData entity) {
		BalanceData balanceData = new BalanceData();
		balanceData.setName("Opening Balance");
		balanceData.setCredit(entity.getBalance());
		balanceData.setDebit(0);
		return balanceData;
	}

	private List<BalanceData> getBalanceDataList(List<Tuple> tuplelist) {
		var balanceDataList = new ArrayList<BalanceData>();
		if (!tuplelist.isEmpty()) {
			for (Tuple tuple : tuplelist) {
				BalanceData balanceData = new BalanceData();
				balanceData.setCredit(tocheckNull(tuple.get("TotalCredit", Long.class)));
				balanceData.setDebit(tocheckNull(tuple.get("TotalDebit", Long.class)));
				balanceData.setName(tocheckNullString(tuple.get("name", String.class)));
				balanceDataList.add(balanceData);
			}
		}
		return balanceDataList;
	}

	private BalanceData getDefaultColumnBalance(List<Tuple> tuplelist, String name) {
		BalanceData balanceData = new BalanceData();
		if (!tuplelist.isEmpty()) {
			Tuple tuple = tuplelist.get(0);
			balanceData.setCredit(tocheckNull(tuple.get("TotalCredit", Long.class)));
			balanceData.setDebit(tocheckNull(tuple.get("TotalDebit", Long.class)));
			balanceData.setName(name);
		}
		return balanceData;
	}

	public int tocheckNull(Object value) {
		return Objects.isNull(value) ? 0 : ((Long) value).intValue();
	}

	public String tocheckNullString(Object value) {
		return Objects.isNull(value) ? null : (value).toString();
	}

	public List<BalanceData> getAlldataforTrailSheet() {
		var balanceDataList = new ArrayList<BalanceData>();
		var entity = thittamDataRepository.findByName();
		var openingBalance = getOpeningBalance(entity);
		balanceDataList.add(openingBalance);
		var bill = thittamDataRepository.getBillData();
		var billBalance = getDefaultColumnBalance(bill, "Vasul Varavu");
		balanceDataList.add(billBalance);
		var commission = thittamDataRepository.getCommissionData();
		var commissonBalance = getDefaultColumnBalance(commission, "Commission");
		balanceDataList.add(commissonBalance);
		var loan = thittamDataRepository.getLoanData();
		var loanBalance = getDefaultColumnBalance(loan, "Loan");
		balanceDataList.add(loanBalance);
		var seetu = thittamDataRepository.getSeetuData();
		var seetuBalance = getDefaultColumnBalance(seetu, "Seetu");
		balanceDataList.add(seetuBalance);
		var excessBalance = thittamDataRepository.getExcessData();
		var excess = getDefaultColumnBalance(excessBalance, "Excess");
		balanceDataList.add(excess);
		var tupleList = thittamDataRepository.getTrialSheet();
		var allDatalist = getBalanceDataList(tupleList);
		for (int i = allDatalist.size() - 1; i >= 0; i--) {
			var data = allDatalist.get(i);
			if (data.getName().contains("LOAN") || data.getName().contains("BILL")
					|| data.getName().contains("COMMISSION") || data.getName().contains("SEETU")
					|| data.getName().contains("EXCESS") || data.getName().contains("Balance")) {
				allDatalist.remove(i);
			}
		}
		balanceDataList.addAll(allDatalist);
		return balanceDataList;
	}

	public GenericResponse getOpeningBalance() {
		GenericResponse genericResponse = new GenericResponse();
		var response = thittamDataRepository.getOpeningBalance();
		if(response == null) {
			genericResponse.setMessage("false");
			return genericResponse;
		}else {
			genericResponse.setMessage("true");
			return genericResponse;
		}
	}

}
