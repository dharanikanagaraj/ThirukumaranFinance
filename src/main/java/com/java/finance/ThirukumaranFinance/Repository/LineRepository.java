package com.java.finance.ThirukumaranFinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
     Line findByLineId(String lineId);

//     @Query("SELECT e FROM line_details e WHERE e.createdOn BETWEEN :fromDate AND :toDate")
//     Line findByTwoDate(LocalDate fromDate, LocalDate toDate);
}
