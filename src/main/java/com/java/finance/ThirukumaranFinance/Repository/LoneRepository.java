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
}
