package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.GenericResponse;
import com.java.finance.ThirukumaranFinance.Domain.LineMemberRequest;
import com.java.finance.ThirukumaranFinance.Domain.UpdateLineMemberRequest;
import com.java.finance.ThirukumaranFinance.Entity.LineMember;
import com.java.finance.ThirukumaranFinance.Repository.LineMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineMemberService {

	private final LineMemberRepository lineMemberRepository;

	public GenericResponse createLineMember(LineMemberRequest lineMemberRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var lineMemberDto = new LineMember();
			lineMemberDto.setLinMemId(getLatestSequenceForLineMember());
			lineMemberDto.setMemberName(lineMemberRequest.getMemberName());
			lineMemberDto.setAddress(lineMemberRequest.getAddress());
			lineMemberDto.setPhoneNo(lineMemberRequest.getPhoneNo());
			lineMemberDto.setPassword(lineMemberRequest.getPassword());
			lineMemberDto.setCreatedOn(LocalDate.now());
			lineMemberDto.setUpdatedOn(LocalDate.now());
			lineMemberDto.setRole("LineMan");
			lineMemberRepository.save(lineMemberDto);
			genericResponse.setMessage("Line Member created Successfully");
			return genericResponse;
		} catch (Exception e) {
			genericResponse.setMessage("Failed to create line member");
			return genericResponse;
		}
	}

	public GenericResponse updateLineMember(UpdateLineMemberRequest lineMemberRequest) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = lineMemberRepository.findByLinMemId(lineMemberRequest.getLineMemId()); // lineMemId should not
																								// be editable
			entity.setMemberName(lineMemberRequest.getMemberName());
			entity.setAddress(lineMemberRequest.getAddress());
			entity.setPassword(lineMemberRequest.getPassword());
			entity.setPhoneNo(lineMemberRequest.getPhoneNo());
			entity.setUpdatedOn(LocalDate.now());
			entity.setRole("LineMan");
			lineMemberRepository.save(entity);
			genericResponse.setMessage("Line Member Updated Successfully");
			return genericResponse;
		} catch (Exception e) {
			genericResponse.setMessage("Failed to update line member");
			return genericResponse;
		}
	}

	public GenericResponse deleteLineMember(String lineMemId) {
		GenericResponse genericResponse = new GenericResponse();
		try {
			var entity = lineMemberRepository.findByLinMemId(lineMemId);
			lineMemberRepository.delete(entity);
			genericResponse.setMessage("Line Member deleted Successfully");
			return genericResponse;
		} catch (Exception e) {
			genericResponse.setMessage("Failed to delete line member");
			return genericResponse;
		}
	}

	public List<LineMember> getAllLineMember() {
		var entity = lineMemberRepository.findByRole("LineMan");
		return entity;
	}

	public LineMember getParticularLineMember(String lineMemId) {
		return lineMemberRepository.findByLinMemId(lineMemId);
	}

	public String getLatestSequenceForLineMember() {
		String newValue;
		String originalValue = lineMemberRepository.findSequence();
		if (originalValue != null && !originalValue.isBlank()) {
			// Extract the numeric part of the string
			String numericPart = originalValue.substring(2);
			// Convert the numeric part to an integer and increment by one
			int incrementedValue = Integer.parseInt(numericPart) + 1;
			// Combine the original prefix with the incremented value
			 newValue = originalValue.substring(0, 2) + String.format("%02d", incrementedValue);
			System.out.println("Incremented value: " + newValue);
		} else {
			 newValue = "Lm01";
		}
		return newValue;
	}

}
