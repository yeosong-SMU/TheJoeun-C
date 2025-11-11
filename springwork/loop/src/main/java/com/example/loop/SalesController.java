package com.example.loop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalesController {
	@Autowired
	SalesService service;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/sales";
	}
	
	@GetMapping("/sales")
	public String salesList(Model model,
			@RequestParam(value = "year", required = false) String yearParam) {
		var years = service.listAvailableYears();
		
		String effective = (yearParam == null || yearParam.isBlank()) 
				? String.valueOf(java.time.Year.now().getValue())
						: yearParam;
		
		Integer yearInt = null;
		if (!"all".equalsIgnoreCase(effective)) {
			try {
				yearInt = Integer.valueOf(effective);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (yearInt == null) {
			model.addAttribute("sales", service.listAllSales());
		} else {
			model.addAttribute("sales", service.listSalesByYear(yearInt));
		}
		
		model.addAttribute("years", years);
		model.addAttribute("year", effective);
		model.addAttribute("yearInt", yearInt);
		return "list";
	}
	
	@PostMapping("/sales/summary")
	public String runSummary(@RequestParam("year") String yearParam) {
		if (yearParam == null || yearParam.isBlank() || "all".equalsIgnoreCase(yearParam)) {
			return "redirect:/sales?year=all";
		}
		int y = Integer.parseInt(yearParam);
		service.runMonthlySummary(y);
		return "redirect:/summary?year=" + y;
	}
	
	@GetMapping("/summary")
	public String summary(Model model, 
			@RequestParam("year") int year) {
		model.addAttribute("year", year);
		model.addAttribute("rows", service.listSummary(year));
		return "summary";
	}
}