package com.java.finance.ThirukumaranFinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.finance.ThirukumaranFinance.Entity.Head;

@Repository
public interface HeadRepository extends JpaRepository<Head, Long> {
	
	Head findById(int id);
}
