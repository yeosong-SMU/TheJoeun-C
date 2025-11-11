package com.example.apartment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApartmentController {
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@GetMapping("/")
	public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<Apartment> list = (keyword == null || keyword.isEmpty()) ? apartmentRepository.findAll()
				: apartmentRepository.findByAddressContainingIgnoreCase(keyword);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		return "list";
	}
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("apartment", new Apartment());
		return "add";
	}
	
	@PostMapping("/add")
	public String addSubmit(@ModelAttribute Apartment apartment) {
		apartmentRepository.save(apartment);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name = "id") Long id, 
			Model model) {
		Apartment apartment = apartmentRepository.findById(id).orElse(null);
		model.addAttribute("apartment", apartment);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String editSubmit(@RequestParam(name="id") Long id, 
			@ModelAttribute Apartment apartment) {
		apartment.setId(id);
		apartmentRepository.save(apartment);
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(name="id") Long id) {
		apartmentRepository.deleteById(id);
		return "redirect:/";
	}
}