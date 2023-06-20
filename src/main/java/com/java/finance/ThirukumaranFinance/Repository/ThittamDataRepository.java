package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.ThittamData;

import jakarta.persistence.Tuple;

@Repository
public interface ThittamDataRepository extends JpaRepository<ThittamData, Long> {
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.extra_head = true and td.date =:date", nativeQuery = true)
	List<ThittamData> findThittamDataforExtraHead(@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name = :name and td.date =:date and td.extra_head = true", nativeQuery = true)
	List<ThittamData> findByNameAndDate(@Param("name")String name,@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name LIKE %:name% and td.date =:date", nativeQuery = true)
	ThittamData findByNameAndDateForBalance(@Param("name")String name,@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name = 'Opening Balance'", nativeQuery = true)
	ThittamData findByName();
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name LIKE '%Balance'  order by td.date desc LIMIT 1", nativeQuery = true)
	ThittamData findByClosingBalance();
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name LIKE '%Balance'  and td.date=:date", nativeQuery = true)
	ThittamData findClosingBalanceWithDate(@Param("date")LocalDate date);

	ThittamData findById(int id);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.date =:date", nativeQuery = true)
	List<ThittamData> findThittamData(@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.date >= :startDate and td.date <= :endDate order by td.date asc, CASE WHEN td.name = 'Opening Balance' THEN 0 WHEN td.name = 'Closing Balance' THEN 2 ELSE 1 END ASC", nativeQuery = true)
	List<ThittamData> getAccountData(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name =:name and td.date >= :startDate and td.date <= :endDate order by td.date asc", nativeQuery = true)
	List<ThittamData> getIndividualData(@Param("name")String name, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT name, SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data GROUP BY name", nativeQuery = true)
	List<Tuple> getTrialSheet();
	
	@Query(value = "SELECT name, SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data where date >= :startDate and date <= :endDate GROUP BY name", nativeQuery = true)
	List<Tuple> getBalanceSheet( @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%BILL'", nativeQuery = true)
	List<Tuple> getBillData();
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%COMMISSION'", nativeQuery = true)
	List<Tuple> getCommissionData();
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%SEETU'", nativeQuery = true)
	List<Tuple> getSeetuData();
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%EXCESS'", nativeQuery = true)
	List<Tuple> getExcessData();
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%LOAN'", nativeQuery = true)
	List<Tuple> getLoanData();
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%BILL' and date >= :startDate and date <= :endDate", nativeQuery = true)
	List<Tuple> getBillDataWithDate(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%COMMISSION' and date >= :startDate and date <= :endDate", nativeQuery = true)
	List<Tuple> getCommissionDataWithDate(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%SEETU' and date >= :startDate and date <= :endDate", nativeQuery = true)
	List<Tuple> getSeetuDataWithDate(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%EXCESS' and date >= :startDate and date <= :endDate", nativeQuery = true)
	List<Tuple> getExcessDataWithDate(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT SUM(debit) AS TotalDebit, SUM(credit) AS TotalCredit FROM thittam_data  where name like '%LOAN' and date >= :startDate and date <= :endDate", nativeQuery = true)
	List<Tuple> getLoanDataWithDate(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
 
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name ='Opening Balance'", nativeQuery = true)
	ThittamData getOpeningBalance();
	
	
}
