package com.java.finance.ThirukumaranFinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.java.finance.ThirukumaranFinance.Entity.LineMember;

@Repository
public interface LineMemberRepository extends JpaRepository<LineMember, Long>{

	LineMember findByLinMemId(String lineMemId);

	List<LineMember> findByRole(String role);

	@Query(value = "SELECT lin_mem_id FROM line_member_details ORDER BY lin_mem_id DESC LIMIT 1", nativeQuery = true)
	String findSequence();
	
}
