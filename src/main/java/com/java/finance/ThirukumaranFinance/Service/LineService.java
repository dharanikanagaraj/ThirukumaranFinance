package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.LineDto;
import com.java.finance.ThirukumaranFinance.Domain.LineRequest;
import com.java.finance.ThirukumaranFinance.Entity.Line;
import com.java.finance.ThirukumaranFinance.Repository.DateCloseRepository;
import com.java.finance.ThirukumaranFinance.Repository.LineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineService {

	private final LineRepository lineRepository;
	private final DateCloseRepository dateCloseRepository;

	public String createLine(LineRequest lineRequest) {
		try {
			System.out.println("line id: " + lineRequest.getLineId());
			System.out.println("line name: " + lineRequest.getLineName());
			var lineDto = new Line();
			lineDto.setLineId(lineRequest.getLineId());
			lineDto.setLineName(lineRequest.getLineName());
			lineDto.setCreatedOn(LocalDate.now());
			lineDto.setUpdatedOn(LocalDate.now());
			lineRepository.save(lineDto);
			return "Line  created Successfully";
		} catch (Exception e) {
			System.out.println("Exception is ::::" + e.getMessage());
			e.printStackTrace();
			return "Failed to create line";
		}
	}

	public String updateLine(LineRequest lineRequest) {
		try {
			Line entity = lineRepository.findByLineId(lineRequest.getLineId()); // lineMemId should not be editable
			entity.setLineName(lineRequest.getLineName());
			entity.setUpdatedOn(LocalDate.now());
			lineRepository.save(entity);
			return "Line Updated Successfully";
		} catch (Exception e) {
			return "Failed to update line";
		}
	}

	public String deleteLine(String lineId) {
		try {
			var entity = lineRepository.findByLineId(lineId);
			lineRepository.delete(entity);
			return "Line deleted Successfully";
		} catch (Exception e) {
			return "Failed to delete line";
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
		var entity = lineRepository.findAll();
		for (int i = 0; i < entity.size(); i++) {
			LineDto lineDto = new LineDto();
			var dateClose = dateCloseRepository.findByLineId(entity.get(i).getLineId());
			if (dateClose.size() > 0 && !dateClose.isEmpty()) {
				lineDto.setDate(dateClose.get(0).getDate());
			}
			lineDto.setLineId(entity.get(i).getLineId());
			lineDto.setLineName(entity.get(i).getLineName());
			response.add(lineDto);
		}
		return response;
	}

}
