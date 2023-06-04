package com.java.finance.ThirukumaranFinance.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.DateCloseRequest;
import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineIdRequest;
import com.java.finance.ThirukumaranFinance.Entity.DateClose;
import com.java.finance.ThirukumaranFinance.Service.DateCloseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restservices/dateClose")
@RequiredArgsConstructor
public class DateCloseController {

	private final DateCloseService dateCloseService;

	@PostMapping("/create")
	public GenericResponse createDateClose(@RequestBody DateCloseRequest dateCloseRequest) {
		var response = dateCloseService.createDateClose(dateCloseRequest);
		return response;
	}

	@PostMapping("/all")
	public List<DateClose> getAllDateClose(@RequestBody LineIdRequest lineIdRequest) {
		var response = dateCloseService.getAllDateClose(lineIdRequest.getLineId());
		return response;
	}
}
