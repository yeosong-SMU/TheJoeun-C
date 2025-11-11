package com.example.chart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockController {
	@Autowired
	StockClient client;
	
	@Autowired
	StockService service;
	
	@GetMapping("/stock")
	public String page(@RequestParam(name = "days", defaultValue = "90") int days,
			@RequestParam(name = "mu", defaultValue = "0.12") double mu,
			@RequestParam(name = "sigma", defaultValue = "0.25") double sigma,
			@RequestParam(name = "s0", defaultValue = "100") double s0,
			@RequestParam(name = "seed", defaultValue = "42") int seed,
			Model model) {
		// local variable type inference(지역변수 타입 추론, java 10부터 가능, 지역변수만 사용 가능)
		var resp = client.sim(days, mu, sigma, s0, seed);
		model.addAttribute("params", resp.get("params"));
		model.addAttribute("data", resp.get("data"));
		return "stock";
	}
	
	@GetMapping("/series")
	public String page(@RequestParam(name = "limit", defaultValue = "200") int limit,
			Model model) {
		Map<String, Object> resp = client.fetch(limit);
		model.addAttribute("limit", limit);
		model.addAttribute("data", resp.get("data"));
		return "series";
	}
	
	@GetMapping("/stockdb")
	public String chart(@RequestParam(name = "limit", defaultValue = "200") int limit,
			Model model) {
		var rows = service.latest(Math.max(10, Math.min(limit, 5000)));
		model.addAttribute("limit", limit);
		model.addAttribute("rows", rows);
		return "stock_db_chart";
	}
}