package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.LineDto;
import com.java.finance.ThirukumaranFinance.Domain.LineIdRequest;
import com.java.finance.ThirukumaranFinance.Domain.LineRequest;
import com.java.finance.ThirukumaranFinance.Entity.Line;
import com.java.finance.ThirukumaranFinance.Service.LineService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/line")
@RequiredArgsConstructor
public class LineController {

	private final LineService lineService;

	@PostMapping("/create")
	public String createLine(@RequestBody LineRequest lineRequest) {
		var response = lineService.createLine(lineRequest);
		return response;
	}

	@PutMapping("/update")
	public String updateLine(@RequestBody LineRequest lineRequest) {
		var response = lineService.updateLine(lineRequest);
		return response;
	}

	@DeleteMapping("/delete")
	public String deleteLine(@RequestBody LineIdRequest lineIdRequest) {
		var response = lineService.deleteLine(lineIdRequest.getLineId());
		return response;
	}
	
	@GetMapping("/particular/line")
	public Line getParticularLine(@RequestBody LineIdRequest lineIdRequest) {
		var response = lineService.getParticularLine(lineIdRequest.getLineId());
		return response;
	}

	@GetMapping("/all")
	public List<Line> getAllLine() {
		var response = lineService.getAllLine();
		return response;
	}

	@GetMapping("/all/dateClose")
	public List<LineDto> getAllLineWithDateClose() {
		var response = lineService.getAllLineWithDateClose();
		return response;
	}
}
