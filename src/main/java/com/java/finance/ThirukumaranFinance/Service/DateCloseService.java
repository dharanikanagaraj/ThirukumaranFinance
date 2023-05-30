package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Entity.DateClose;
import com.java.finance.ThirukumaranFinance.Repository.DateCloseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateCloseService {

	private final DateCloseRepository dateCloseRepository;

	public String createDateClose(DateCloseRequest dateCloseRequest) {
		try {
			var dateClose = new DateClose();
			dateClose.setCreatedOn(LocalDate.now());
			dateClose.setLineId(dateCloseRequest.getLineId());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate parsedDate = LocalDate.parse(dateCloseRequest.getDate(), formatter);
			dateClose.setDate(parsedDate);
			dateCloseRepository.save(dateClose);
			return "Date close  created Successfully";
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			return "Failed to create dateClose";
		}
	}

	public List<DateClose> getAllDateClose(String lineId) {
		var entity = dateCloseRepository.findByLineId(lineId);
		return entity;
	}

}
