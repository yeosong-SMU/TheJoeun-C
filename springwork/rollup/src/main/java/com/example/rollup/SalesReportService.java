package com.example.rollup;

import java.time.LocalDate;
import java.util.List;

public interface SalesReportService {
	List<SalesViewRow> byDate(LocalDate from, LocalDate to);
	List<SalesViewRow> byRegion(LocalDate from, LocalDate to);
	List<SalesViewRow> byCategory(LocalDate from, LocalDate to);
}