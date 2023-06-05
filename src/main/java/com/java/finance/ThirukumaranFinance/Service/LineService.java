package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineDto;
import com.java.finance.ThirukumaranFinance.Domain.LineRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateLineRequest;
import com.java.finance.ThirukumaranFinance.Entity.Line;
import com.java.finance.ThirukumaranFinance.Repository.DateCloseRepository;
import com.java.finance.ThirukumaranFinance.Repository.LineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineService {

	private final LineRepository lineRepository;
	private final DateCloseRepository dateCloseRepository;

	public GenericResponse createLine(LineRequest lineRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var lineId = getLatestSequenceForLine();
			System.out.println("line name: " + lineRequest.getLineName());
			var lineDto = new Line();
			lineDto.setLineId(lineId);
			lineDto.setLineName(lineRequest.getLineName());
			lineDto.setCreatedOn(LocalDate.now());
			lineDto.setUpdatedOn(LocalDate.now());
			lineRepository.save(lineDto);
			genericResponse.setMessage("Line  created Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to create line");
			return genericResponse;
		}
	}

	public GenericResponse updateLine(UpdateLineRequest lineRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			Line entity = lineRepository.findByLineId(lineRequest.getLineId()); // lineMemId should not be editable
			entity.setLineName(lineRequest.getLineName());
			entity.setUpdatedOn(LocalDate.now());
			lineRepository.save(entity);
			genericResponse.setMessage("Line Updated Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to update line");
			return genericResponse;
		}
	}

	public GenericResponse deleteLine(String lineId) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = lineRepository.findByLineId(lineId);
			lineRepository.delete(entity);
			genericResponse.setMessage("Line deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			genericResponse.setMessage("Failed to delete line");
			return genericResponse;
		}
	}

	public List<Line> getAllLine() {
		var entity = lineRepository.findAll();
		return entity;
	}

	public Line getParticularLine(String lineId) {
		return lineRepository.findByLineId(lineId); // lineMemId should not be editable

	}

	public List<LineDto> getAllLineWithDateClose() {
		var response = new ArrayList<LineDto>();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		var entity = lineRepository.findAll();
		for (int i = 0; i < entity.size(); i++) {
			LineDto lineDto = new LineDto();
			var dateClose = dateCloseRepository.findByLineId(entity.get(i).getLineId());
			if (dateClose.size() > 0 && !dateClose.isEmpty()) {
				var date = dateClose.get(0).getDate().plusDays(1);
				String strDate = date.format(formatters);
				lineDto.setDate(strDate);
			}
			lineDto.setLineId(entity.get(i).getLineId());
			lineDto.setLineName(entity.get(i).getLineName());
			response.add(lineDto);
		}
		return response;
	}

	public String getLatestSequenceForLine() {
		String newValue;
		String originalValue = lineRepository.findSequence();
		if (originalValue != null && !originalValue.isBlank()) {
			// Extract the numeric part of the string
			String numericPart = originalValue.substring(2);
			// Convert the numeric part to an integer and increment by one
			int incrementedValue = Integer.parseInt(numericPart) + 1;
			// Combine the original prefix with the incremented value
			 newValue = originalValue.substring(0, 2) + String.format("%02d", incrementedValue);
			System.out.println("Incremented value: " + newValue);
		} else {
			 newValue ="Ln01";
		}
		return newValue;
	}

}
