package com.example.cluster;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CustomerController {
	@Autowired
	CustomerRepository repo;
	
	@Autowired
	CustomerService pipeline;
	
	@GetMapping("/")
	public String list(Model model,
			@RequestParam(value = "msg", required = false) String msg) {
		List<Customer> customers = repo.findAll();
		model.addAttribute("customers", customers);
		model.addAttribute("msg", msg);
		return "list";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) throws Exception {
		int count = pipeline.ingestAndLabel(file).size();
		String msg = URLEncoder.encode(count + "건 처리 완료", StandardCharsets.UTF_8);
		return "redirect:/?msg=" + msg;
	}
	
	@GetMapping("/charts")
	public String charts(Model model) {
		List<Customer> customers = repo.findAll();
		Map<Integer, Long> dist = customers
				.stream()
				.map(Customer::getClusterLabel)
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(k -> k, Collectors.counting()));
		Map<Integer, List<Map<String, Double>>> incomeSpendByCluster = new HashMap<>();
		Map<Integer, List<Map<String, Double>>> ageSpendByCluster = new HashMap<>();
		
		for (Customer c : customers) {
			Integer k = c.getClusterLabel();
			if (k == null)
				continue;
			
			incomeSpendByCluster.computeIfAbsent(k, __ -> new ArrayList<>())
			.add(Map.of("x", c.getIncome(), "y", c.getSpend()));
			
			ageSpendByCluster.computeIfAbsent(k, __ -> new ArrayList<>())
			.add(Map.of("x", c.getAge(), "y", c.getSpend()));
		}
		
		model.addAttribute("dist", dist);
		model.addAttribute("incomeSpendByCluster", incomeSpendByCluster);
		model.addAttribute("ageSpendByCluster", ageSpendByCluster);
		return "chart";
	}
}