package com.example.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {
	@Autowired
	private InsuranceRepository insuranceRepository;
	
	@GetMapping("/list")
	public String getInsuranceList(@RequestParam(name="page", defaultValue = "1") int page, 
			@RequestParam(name="sortBy", defaultValue = "age") String sortBy,
			Model model) {
		List<Insurance> insurances = insuranceRepository.findAll();
		model.addAttribute("insurances", insurances);
		model.addAttribute("currentPage", page);
		model.addAttribute("sortBy", sortBy);
		return "list";
	}
	
	@GetMapping("/add")
	public String addInsuranceForm() {
		return "add";
	}
	
	@PostMapping("/save")
	public String saveInsurance(@ModelAttribute Insurance insurance) {
		insuranceRepository.save(insurance);
		return "redirect:/insurance/list";
	}
	
	@GetMapping("/edit/{id}")
	public String editInsuranceForm(@PathVariable(name="id") Long id, 
			Model model) {
		Insurance insurance = insuranceRepository.findById(id).orElse(null);
		model.addAttribute("insurance", insurance);
		return "edit";
	}
	
	@PostMapping("/update")
	public String updateInsurance(@ModelAttribute Insurance insurance) {
		insuranceRepository.save(insurance);
		return "redirect:/insurance/list";
	}
	
	@PostMapping("/delete")
	public String deleteInsurance(@RequestParam(name = "id") Long id) {
		insuranceRepository.deleteById(id);
		return "redirect:/insurance/list";
	}
}