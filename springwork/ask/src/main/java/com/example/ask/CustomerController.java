package com.example.ask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
	@Autowired
	private CustomerQueryRepository repo;
	
	@Autowired
	private FastApiService service;
	
	@GetMapping
	public String list(Model model) {
		List<CustomerQuery> items = repo.findAll(Sort.by(Sort.Direction.ASC, "urgencyClass"));
		model.addAttribute("queries", items);
		return "list";
	}
	
	@GetMapping("/form")
	public String form() {
		return "form";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute CustomerQuery customerQuery) {
		QueryResponse response = service.classifyText(customerQuery.getText());
		
		customerQuery.setLabel(response.getLabel());
		customerQuery.setUrgencyClass(response.getUrgencyClass());
		
		repo.save(customerQuery);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, 
			Model model) {
		CustomerQuery customerQuery = repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid query ID: " + id));
		model.addAttribute("customerQuery", customerQuery);
		return "edit";
	}
	
	@PostMapping("/update/{id}")
	public String updateQuery(@PathVariable("id") Long id, 
			@ModelAttribute CustomerQuery updatedQuery) {
		CustomerQuery existingQuery = repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid query ID: " + id));
		
		existingQuery.setText(updatedQuery.getText());
		existingQuery.setLabel(updatedQuery.getLabel());
		existingQuery.setUrgencyClass(updatedQuery.getUrgencyClass());
		
		repo.save(existingQuery);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		CustomerQuery customerQuery = repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid query ID: " + id));
		
		repo.delete(customerQuery);
		return "redirect:/";
	}
}