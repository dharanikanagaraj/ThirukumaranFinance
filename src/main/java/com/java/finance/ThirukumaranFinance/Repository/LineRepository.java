package com.java.finance.ThirukumaranFinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    
	Line findByLineId(String lineId);

	@Query(value = "SELECT line_id FROM line_details ORDER BY line_id DESC LIMIT 1", nativeQuery = true)
	String findSequence();
}
