package com.example.rollup;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReportViewController {
	private final SalesReportService service;
	
	private LocalDate defFrom() {
		return LocalDate.now().withDayOfMonth(1);
	}
	
	private LocalDate defTo() {
		return LocalDate.now();
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/reports/date";
	}
	
	@GetMapping("/reports/date")
	public String byDate(
			@RequestParam(value = "from", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(value = "to", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			Model model) {
		if (from == null)
			from = defFrom();
		if (to == null)
			to = defTo();
		model.addAttribute("rows", service.byDate(from, to));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "list_date";
	}
	
	@GetMapping("/reports/region")
	public String byRegion(
			@RequestParam(value = "from", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(value = "to", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			Model model) {
		if (from == null)
			from = defFrom();
		if (to == null)
			to = defTo();
		model.addAttribute("rows", service.byRegion(from, to));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "list_region";
	}
	
	@GetMapping("/reports/category")
	public String byCategory(
			@RequestParam(value = "from", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(value = "to", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			Model model) {
		if (from == null)
			from = defFrom();
		if (to == null)
			to = defTo();
		model.addAttribute("rows", service.byCategory(from, to));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "list_category";
	}
}