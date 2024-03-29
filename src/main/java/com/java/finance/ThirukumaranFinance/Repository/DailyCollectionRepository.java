package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Dailycollection;

@Repository
public interface DailyCollectionRepository extends JpaRepository<Dailycollection, Long> {

	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.line_id = :lineId and dc.date = :date", nativeQuery = true)
	List<Dailycollection> getAllDailyCollection(@Param("lineId") String lineId, @Param("date") LocalDate date);
	
	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.line_id = :lineId and dc.date = :date and dc.amount_paid='0'", nativeQuery = true)
	List<Dailycollection> getAllDailyCollectionNotPaid(@Param("lineId") String lineId, @Param("date") LocalDate date);
 
	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.loan_id = :loanId and dc.date = :date", nativeQuery = true)
	Dailycollection getByLoanId(@Param("loanId")int loanId, @Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.loan_id = :loanId", nativeQuery = true)
	List<Dailycollection> getAllByLoanId(@Param("loanId")int loanId);
	
	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.loan_id = :loanId order by created_on desc", nativeQuery = true)
	List<Dailycollection> getAllByLoanIdInDesc(@Param("loanId")int loanId);

	@Query(value = "SELECT sum(amount_paid) FROM daily_amount_collection dc WHERE dc.line_id = :lineId and dc.date = :date", nativeQuery = true)
	Integer getTotalAmountForAllCollection(@Param("lineId") String lineId, @Param("date") LocalDate date);

	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.line_id = :lineId and dc.date >= :startDate and dc.date < :endDate and dc.amount_paid=0", nativeQuery = true)
	List<Dailycollection> getNotPaidUser(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT * FROM daily_amount_collection dc WHERE dc.line_id = :lineId and dc.date >= :startDate and dc.date <= :endDate and dc.loan_id = :loanId order by created_on asc", nativeQuery = true)
	List<Dailycollection> getAmountPaidForParticularDateRange(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("loanId")int loanId);
	
}
