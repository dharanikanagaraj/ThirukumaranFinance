package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.DateClose;

@Repository
public interface DateCloseRepository extends JpaRepository<DateClose, Long> {

	@Query(value = "SELECT * FROM date_close dc WHERE dc.line_id = :lineId order by dc.created_on desc", nativeQuery = true)
	List<DateClose> findByLineId(@Param("lineId") String lineId);

	@Query(value = "SELECT * FROM date_close dc WHERE dc.line_id = :lineId and dc.date =:date", nativeQuery = true)
	List<DateClose> findByDate(@Param("lineId") String lineId,@Param("date")LocalDate parsedDate);

}
