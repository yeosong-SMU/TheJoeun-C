package com.example.chart;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StockPoint {
	@Id
	private LocalDateTime ts;
	
	private Double y;
}