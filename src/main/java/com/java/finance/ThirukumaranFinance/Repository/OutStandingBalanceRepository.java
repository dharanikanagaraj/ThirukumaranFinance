package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.OutStandingBalance;

@Repository
public interface OutStandingBalanceRepository extends JpaRepository<OutStandingBalance, Long> {

	@Query(value = "SELECT * FROM outstanding_balance ob WHERE ob.date =:date", nativeQuery = true)
	List<OutStandingBalance> getOutStandingBalanceForDate(@Param("date") LocalDate date);
}
