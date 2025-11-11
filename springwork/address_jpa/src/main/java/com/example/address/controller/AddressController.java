package com.example.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.address.entity.AddressBook;
import com.example.address.repository.AddressBookRepository;

@Controller
public class AddressController {
	@Autowired
	private AddressBookRepository repo;
	
	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("list", repo.findAll());
		return "list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("address", new AddressBook());
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String edit(@PathVariable(name = "id") Long id, 
			Model model) {
		AddressBook ab = repo.findById(id).orElseThrow();
		model.addAttribute("address", ab);
		return "form";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute AddressBook address) {
		repo.save(address);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Long id) {
		repo.deleteById(id);
		return "redirect:/";
	}
}