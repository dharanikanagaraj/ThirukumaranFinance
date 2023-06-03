package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.DailyTotal;

@Repository
public interface DailyTotalRepository extends JpaRepository<DailyTotal, Long> {

	@Query(value = "SELECT * FROM daily_total dt WHERE dt.line_id = :lineId and dt.date >= :startDate and dt.date <= :endDate", nativeQuery = true)
	List<DailyTotal> findTotalForParticularRange(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

	

}
