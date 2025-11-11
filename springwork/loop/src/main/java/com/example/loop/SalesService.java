package com.example.loop;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class SalesService {
	@Autowired
	SalesRepository srepo;
	
	@Autowired
	SalesSummaryRepository smrepo;
	
	@Autowired
	EntityManager em;
	
	public List<Sales> listAllSales() {
		return srepo.findAll(Sort.by(Sort.Direction.ASC, "saleDate"));
	}
	
	public List<Sales> listSalesByYear(int year) {
		LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
		LocalDateTime end = LocalDate.of(year + 1, 1, 1).atStartOfDay();
		return srepo.findBySaleDateBetweenOrderBySaleDateAsc(start, end);
	}
	
	public List<Integer> listAvailableYears() {
		List<Number> yearsNum = em
				.createNativeQuery("select distinct extract(year from sale_date) as y from sales order by y")
				.getResultList();
		return yearsNum.stream().map(n -> n.intValue()).toList();
	}
	
	public List<SalesSummary> listSummary(int year) {
		return smrepo.findByYearOrderByMonth(year);
	}
	
	@Transactional
	public void runMonthlySummary(int year) {
		StoredProcedureQuery spq = em.createStoredProcedureQuery("proc_sales_summary");
		spq.registerStoredProcedureParameter("p_year", Integer.class, ParameterMode.IN);
		spq.setParameter("p_year", year);
		spq.execute();
	}
}