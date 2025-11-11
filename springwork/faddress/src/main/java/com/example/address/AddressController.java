package com.example.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
	private AddressBookRepository repo;
	
	@GetMapping
	public List<AddressBook> findAll() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public AddressBook findById(@PathVariable(name = "id") Long id) {
		return repo.findById(id).orElseThrow();
	}
	
	@PostMapping
	public AddressBook create(@RequestBody AddressBook ab) {
		return repo.save(ab);
	}
	
	@PutMapping("/{id}")
	public AddressBook update(@PathVariable(name = "id") Long id, 
			@RequestBody AddressBook ab) {
		ab.setId(id);
		return repo.save(ab);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		repo.deleteById(id);
	}
}