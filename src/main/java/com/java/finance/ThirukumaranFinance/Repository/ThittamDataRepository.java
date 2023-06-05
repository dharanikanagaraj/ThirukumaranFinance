package com.java.finance.ThirukumaranFinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.ThittamData;

@Repository
public interface ThittamDataRepository extends JpaRepository<ThittamData, Long> {
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.extra_head = true and td.date =:date", nativeQuery = true)
	List<ThittamData> findThittamDataforExtraHead(@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name = :name and td.date =:date and td.extra_head = true", nativeQuery = true)
	List<ThittamData> findByNameAndDate(@Param("name")String name,@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name LIKE %:name% and td.date =:date", nativeQuery = true)
	ThittamData findByNameAndDateForBalance(@Param("name")String name,@Param("date")LocalDate date);

	ThittamData findById(int id);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.date =:date", nativeQuery = true)
	List<ThittamData> findThittamData(@Param("date")LocalDate date);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.date >= :startDate and td.date <= :endDate", nativeQuery = true)
	List<ThittamData> getAccountData(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
	
	@Query(value = "SELECT * FROM thittam_data td WHERE td.name =:name and td.date >= :startDate and td.date <= :endDate", nativeQuery = true)
	List<ThittamData> getIndividualData(@Param("name")String name, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
