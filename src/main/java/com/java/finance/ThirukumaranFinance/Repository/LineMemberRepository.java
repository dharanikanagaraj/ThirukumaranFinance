package com.java.finance.ThirukumaranFinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.java.finance.ThirukumaranFinance.Entity.LineMember;

@Repository
public interface LineMemberRepository extends JpaRepository<LineMember, Long>{

	LineMember findByLinMemId(String lineMemId);

	List<LineMember> findByRole(String role);
	
}
