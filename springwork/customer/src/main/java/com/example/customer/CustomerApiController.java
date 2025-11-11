package com.example.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerApiController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ConsultationRepository consultationRepository;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable(name="id") Long id) {
		return customerRepository.findById(id).orElse(null);
	}
	
	@PostMapping("/customer")
	public Customer saveCustomer(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	@PutMapping("/customer/{id}")
	public Customer updateCustomer(@PathVariable(name="id") Long id, 
			@RequestBody Customer customer) {
		Customer existing = customerRepository.findById(id).orElse(null);
		if(existing != null) {
			existing.setName(customer.getName());
			existing.setPhone(customer.getPhone());
			existing.setEmail(customer.getEmail());
			return customerRepository.save(existing);
		}
		return null;
	}
	
	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable(name = "id") Long id) {
		customerRepository.deleteById(id);
	}
	
	@GetMapping("/customer/{id}/consultations")
	public List<Consultation> vetConsultations(@PathVariable(name="id") Long id) {
		return consultationRepository.findByCustomerId(id);
	}
	
	@PostMapping("/consultation")
	public Consultation saveConsultation(@RequestBody Consultation consultation) {
		return consultationRepository.save(consultation);
	}
	
	@DeleteMapping("/consultation/{id}")
	public void deleteConsultation(@PathVariable(name="id") Long id) {
		consultationRepository.deleteById(id);
	}
	
	@GetMapping("/consultation/search")
	public List<Consultation> searchByCustomerId(@RequestParam(name="customerId") Long customerId) {
		return consultationRepository.findByCustomerId(customerId);
	}
	
	@PutMapping("/consultations/{id}")
	public Consultation updateConsultation(@PathVariable(name="id") Long id, 
			@RequestBody Consultation updated) {
		return consultationRepository.findById(id).map(c -> {
			c.setContent(updated.getContent());
			c.setConsultationDate(updated.getConsultationDate());
			return consultationRepository.save(c);
		}).orElseThrow(() -> new RuntimeException("Not found"));
	}
}