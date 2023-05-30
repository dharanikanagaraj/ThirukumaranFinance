package com.java.finance.ThirukumaranFinance.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.finance.ThirukumaranFinance.Domain.LineMemberRequest;
import com.java.finance.ThirukumaranFinance.Entity.LineMember;
import com.java.finance.ThirukumaranFinance.Repository.LineMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineMemberService {

	private final LineMemberRepository lineMemberRepository;

	public String createLineMember(LineMemberRequest lineMemberRequest) {
		try {
			var lineMemberDto = new LineMember();
			lineMemberDto.setLinMemId(lineMemberRequest.getLineMemId());
			lineMemberDto.setMemberName(lineMemberRequest.getMemberName());
			lineMemberDto.setAddress(lineMemberRequest.getAddress());
			lineMemberDto.setPhoneNo(lineMemberRequest.getPhoneNo());
			lineMemberDto.setPassword(lineMemberRequest.getPassword());
			lineMemberDto.setCreatedOn(LocalDate.now());
			lineMemberDto.setUpdatedOn(LocalDate.now());
			lineMemberDto.setRole("LineMan");
			lineMemberRepository.save(lineMemberDto);
			return "Line Member created Successfully";
		} catch (Exception e) {
			return "Failed to create line member";
		}
	}

	public String updateLineMember(LineMemberRequest lineMemberRequest) {
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
			return "Line Member Updated Successfully";
		} catch (Exception e) {
			return "Failed to update line member";
		}
	}

	public String deleteLineMember(String lineMemId) {
		try {
			var entity = lineMemberRepository.findByLinMemId(lineMemId);
			lineMemberRepository.delete(entity);
			return "Line Member deleted Successfully";
		} catch (Exception e) {
			return "Failed to delete line member";
		}
	}

	public List<LineMember> getAllLineMember() {
		var entity = lineMemberRepository.findByRole("LineMan");
		return entity;
	}

	public LineMember getParticularLineMember(String lineMemId) {
		return lineMemberRepository.findByLinMemId(lineMemId);
	}

}
