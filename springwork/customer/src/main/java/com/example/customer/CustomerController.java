package com.example.customer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ConsultationRepository consultationRepository;
	
	@GetMapping("/")
	public String list(Model model) {
		List<Customer> list = customerRepository.findAll();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/write")
	public String writeForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "write";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute Customer customer) {
		customerRepository.save(customer);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name = "id") Long id, 
			Model model) {
		Customer customer = customerRepository.findById(id).orElse(null);
		List<Consultation> consultations = consultationRepository.findByCustomerId(id);
		model.addAttribute("customer", customer);
		model.addAttribute("consultations", consultations);
		return "edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Customer customer) {
		customerRepository.save(customer);
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") Long id) {
		customerRepository.deleteById(id);
		return "redirect:/";
	}
	
	@PostMapping("/consultation/add")
	public String addConsultation(@RequestParam(name="customerId") Long customerId, 
			@RequestParam(name="content") String content) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		Consultation c = new Consultation();
		c.setCustomer(customer);
		c.setContent(content);
		c.setConsultationDate(java.time.LocalDate.now());
		consultationRepository.save(c);
		return "redirect:/edit/" + customerId;
	}
	
	@PostMapping("/consultation/update")
	public String updateConsultation(@RequestParam(name = "id") Long id, 
			@RequestParam(name = "customerId") Long customerId, 
			@RequestParam(name = "content") String content, 
			@RequestParam(name = "consultationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		Consultation c = consultationRepository.findById(id).orElse(null);
		if(c != null) {
			c.setContent(content);
			c.setConsultationDate(date);
			consultationRepository.save(c);
		}
		return "redirect:/edit/" + customerId;
	}
	
	@GetMapping("/consultation/delete/{id}")
	public String deleteConsultation(@PathVariable(name="id") Long id) {
		Consultation c = consultationRepository.findById(id).orElse(null);
		Long customerId = c.getCustomer().getId();
		consultationRepository.deleteById(id);
		return "redirect:/edit/" + customerId;
	}
}