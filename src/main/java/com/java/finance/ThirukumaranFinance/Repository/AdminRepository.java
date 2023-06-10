package com.java.finance.ThirukumaranFinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	@Query(value = "SELECT admin_id FROM admin ORDER BY admin_id DESC LIMIT 1", nativeQuery = true)
	String findSequence();

	Admin findByAdminId(String adminId);

	Admin findByPhoneNo(String phoneNo);
}
