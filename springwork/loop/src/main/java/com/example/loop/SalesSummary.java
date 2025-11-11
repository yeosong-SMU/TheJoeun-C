package com.example.loop;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long summaryId;
	
	private Integer year;
	private Integer month;
	private BigDecimal totalAmt;
	private String remark;
}