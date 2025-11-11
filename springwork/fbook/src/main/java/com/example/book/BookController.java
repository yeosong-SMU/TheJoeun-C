package com.example.book;

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
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookRepository repo;
	
	@GetMapping
	public List<Book> findAll() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Book findById(@PathVariable(name = "id") Long id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id: " + id));
	}
	
	@PostMapping
	public Book create(@RequestBody Book book) {
		return repo.save(book);
	}
	
	@PutMapping("/{id}")
	public Book update(@PathVariable(name = "id") Long id, 
			@RequestBody Book book) {
		book.setId(id);
		return repo.save(book);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		repo.deleteById(id);
	}
}