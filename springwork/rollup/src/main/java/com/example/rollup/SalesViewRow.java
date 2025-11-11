package com.example.rollup;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesViewRow {
	private LocalDate orderDate;
	private String regionLabel;
	private String categoryLabel;
	private Double totalAmount;
}