package com.example.loop;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
	List<Sales> findBySaleDateBetweenOrderBySaleDateAsc(LocalDateTime start, LocalDateTime end);
}