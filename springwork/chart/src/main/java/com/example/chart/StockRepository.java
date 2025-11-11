package com.example.chart;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockPoint, LocalDateTime> {
	List<StockPoint> findAllByOrderByTsDesc(Pageable pageable);
}