package com.example.loop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {
	List<SalesSummary> findByYearOrderByMonth(Integer year);
}