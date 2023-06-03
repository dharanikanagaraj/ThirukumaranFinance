package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Entity.DailyTotal;
import com.java.finance.ThirukumaranFinance.Entity.DateClose;
import com.java.finance.ThirukumaranFinance.Repository.DailyCollectionRepository;
import com.java.finance.ThirukumaranFinance.Repository.DailyTotalRepository;
import com.java.finance.ThirukumaranFinance.Repository.DateCloseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateCloseService {

	private final DateCloseRepository dateCloseRepository;
	
	private final DailyCollectionRepository dailyCollectionRepository;
	
	private final DailyTotalRepository dailyTotalRepository;

	public String createDateClose(DateCloseRequest dateCloseRequest) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate parsedDate = LocalDate.parse(dateCloseRequest.getDate(), formatter);
			var date = dateCloseRepository.findByDate(dateCloseRequest.getLineId(),parsedDate);
			var dateClose = new DateClose();
			dateClose.setCreatedOn(LocalDate.now());
			dateClose.setLineId(dateCloseRequest.getLineId());
			dateClose.setDate(parsedDate);
			dateCloseRepository.save(dateClose);
			if(date == null) {
			updateDailyTotal(dateClose.getLineId(),dateClose.getDate());
			}
			return "Date close  created Successfully";
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			return "Failed to create dateClose";
		}
	}

	private void updateDailyTotal(String lineId, LocalDate date) {
		var response = dailyCollectionRepository.getTotalAmountForAllCollection(lineId, date);
		if(response !=null && response >= 0) {
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
