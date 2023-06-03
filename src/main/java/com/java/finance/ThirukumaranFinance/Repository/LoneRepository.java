package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Loan;

@Repository
public interface LoneRepository extends JpaRepository<Loan, Long> {

	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.loan_no = :loanNo", nativeQuery = true)
	Loan findByLoanNoAndLineId(@Param("lineId")String lineId,@Param("loanNo")String loanNo);
    
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = true and current_loan_date = :loanDate", nativeQuery = true)
	List<Loan> findAllLoanForPresentDay(@Param("lineId") String lineId, @Param("loanDate") LocalDate date);

	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = true and ld.daily_update = false order by order_no asc ", nativeQuery = true)
	List<Loan> getAllActiveLoan(@Param("lineId")String lineId);

	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = true", nativeQuery = true)
	List<Loan> getAllActiveLoanForReport(@Param("lineId")String lineId);
	
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and  ld.is_loan_active = false and ld.excess_amount !='0'", nativeQuery = true)
	List<Loan> getLoanWithExcessAmount(@Param("lineId")String lineId);
 
	@Query(value = "SELECT ld.* FROM user_loan_details ld WHERE ld.line_id = :lineId AND ld.loan_closed_date = (SELECT MAX(ld2.loan_closed_date) FROM user_loan_details ld2 where ld2.user_no = ld.user_no) AND ld.user_no IN (SELECT user_no from user_loan_details  GROUP BY user_no HAVING COUNT(*) >0 AND COUNT(*) = SUM (CASE WHEN is_loan_active = false THEN 1 ELSE 0 END))", nativeQuery = true)
	List<Loan> getAllClosedParty(@Param("lineId")String lineId);
	
	@Query(value = "SELECT DISTINCT user_no, name FROM user_loan_details ld WHERE ld.line_id = :lineId", nativeQuery = true)
	List<Loan> getAllDistinctUser(@Param("lineId")String lineId);
	
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.user_no =:userNo and ld.is_loan_active = false", nativeQuery = true)
	List<Loan> getAllClosedLoanForUser(@Param("lineId")String lineId, @Param("userNo")String userNo);
	
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.current_loan_date < :date and ld.is_loan_active = true", nativeQuery = true)
	List<Loan> getNipLoanList(@Param("lineId")String lineId, @Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = false and ld.loan_closed_date >= :startDate and ld.loan_closed_date <= :endDate", nativeQuery = true)
	List<Loan> getClosedLoanForParticularDateRange(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = true and ld.current_loan_date >= :startDate and ld.current_loan_date <= :endDate", nativeQuery = true)
	List<Loan> getActiveLoanForParticularDateRange(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT * FROM user_loan_details ld WHERE ld.line_id = :lineId and ld.is_loan_active = true and ld.current_loan_date >= :startDate and ld.current_loan_date < :endDate", nativeQuery = true)
	List<Loan> getLoanForLedger(@Param("lineId")String lineId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

}
