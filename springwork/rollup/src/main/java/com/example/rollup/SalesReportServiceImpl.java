package com.example.rollup;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {
	@PersistenceContext
	private final EntityManager em;
	
	@Override
	public List<SalesViewRow> byDate(LocalDate from, LocalDate to) {
		StoredProcedureQuery spq = em.createStoredProcedureQuery("sales_rollup_by_date");
		spq.registerStoredProcedureParameter("p_from", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_to", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR);
		spq.setParameter("p_from", Date.valueOf(from));
		spq.setParameter("p_to", Date.valueOf(to));
		List<Object[]> rows = spq.getResultList();
		List<SalesViewRow> list = new ArrayList<>();
		for(Object[] r : rows) {
			list.add(SalesViewRow.builder().orderDate(toLocalDate(r[0]))
					.regionLabel((String) r[1])
					.categoryLabel((String) r[2])
					.totalAmount(((Number) r[3]).doubleValue()).build());
		}
		return list;
	}
	
	@Override
	public List<SalesViewRow> byRegion(LocalDate from, LocalDate to) {
		StoredProcedureQuery spq = em.createStoredProcedureQuery("sales_rollup_by_region");
		spq.registerStoredProcedureParameter("p_from", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_to", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR);
		spq.setParameter("p_from", Date.valueOf(from));
		spq.setParameter("p_to", Date.valueOf(to));
		List<Object[]> rows = spq.getResultList();
		List<SalesViewRow> list = new ArrayList<>();
		for(Object[] r : rows) {
			list.add(SalesViewRow.builder().regionLabel((String) r[0])
					.orderDate(toLocalDate(r[1]))
					.categoryLabel((String) r[2])
					.totalAmount(((Number) r[3]).doubleValue()).build());
		}
		return list;
	}
	
	@Override
	public List<SalesViewRow> byCategory(LocalDate from, LocalDate to) {
		StoredProcedureQuery spq = em.createStoredProcedureQuery("sales_rollup_by_category");
		spq.registerStoredProcedureParameter("p_from", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_to", Date.class, ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR);
		spq.setParameter("p_from", Date.valueOf(from));
		spq.setParameter("p_to", Date.valueOf(to));
		List<Object[]> rows = spq.getResultList();
		List<SalesViewRow> list = new ArrayList<>();
		for(Object[] r : rows) {
			list.add(SalesViewRow.builder().categoryLabel((String) r[0])
					.orderDate(toLocalDate(r[1]))
					.regionLabel((String) r[2])
					.totalAmount(((Number) r[3]).doubleValue()).build());
		}
		return list;
	}
	
	private java.time.LocalDate toLocalDate(Object col) {
		if (col == null)
			return null;
		if (col instanceof java.sql.Timestamp ts) {
			return ts.toLocalDateTime().toLocalDate();
		} else if (col instanceof java.sql.Date d) {
			return d.toLocalDate();
		} else if (col instanceof java.util.Date ud) {
			return ud.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		} else if (col instanceof java.time.LocalDate ld) {
			return ld;
		} else if (col instanceof java.time.LocalDateTime ldt) {
			return ldt.toLocalDate();
		}
		throw new IllegalArgumentException("Unsupported date type: " + col.getClass());
	}
}