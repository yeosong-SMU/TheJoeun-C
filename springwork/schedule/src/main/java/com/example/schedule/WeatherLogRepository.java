package com.example.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
	List<WeatherLog> findAllByOrderByCapturedAtAsc();
	
	List<WeatherLog> findByCapturedAtBetweenOrderByCapturedAt(
			LocalDateTime startInclusive, 
			LocalDateTime endExclusive);
}