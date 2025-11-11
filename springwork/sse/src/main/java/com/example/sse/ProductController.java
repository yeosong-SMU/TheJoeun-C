package com.example.sse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductRepository repo;
	private final InventoryService service;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("items", repo.findAll());
		return "list";
	}
	
	@PostMapping("/stock/{id}")
	@ResponseBody
	public Product updateStock(@PathVariable("id") Long id,
			@RequestParam("qty") int qty) {
		return service.setStockQty(id, qty);
	}
}