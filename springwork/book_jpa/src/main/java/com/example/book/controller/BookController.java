package com.example.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book.model.Book;
import com.example.book.repository.BookRepository;

@Controller
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	public String getBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "list";
	}
	
	@GetMapping("/search")
	public String searchBooks(@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestParam(value = "search", defaultValue="") String search,
			Model model) {
		List<Book> books = List.of();
		
		if(search != null && !search.trim().isEmpty()) {
			if("title".equals(searchCriteria)) {
				books = bookRepository.findByTitleContaining(search);
			} else if ("author".equals(searchCriteria)) {
				books = bookRepository.findByAuthorContaining(search);
			} else if ("genre".equals(searchCriteria)) {
				books = bookRepository.findByGenreContaining(search);
			}
		} else {
			books = bookRepository.findAll();
		}
		
		model.addAttribute("books", books);
		model.addAttribute("search", search);
		model.addAttribute("searchCriteria", searchCriteria);
		return "list";
	}
	
	@GetMapping("/write")
	public String showAddBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "write";
	}
	
	@PostMapping("/write")
	public String addBook(@ModelAttribute Book book) {
		bookRepository.save(book);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditBookForm(@PathVariable(name="id") Long id, 
			Model model) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
		model.addAttribute("book", book);
		return "edit";
	}
	
	@PostMapping("/update")
	public String updateBook(@RequestParam(name="id") Long id, 
			@ModelAttribute Book book) {
		book.setId(id);
		bookRepository.save(book);
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deleteBook(@RequestParam(name="id") Long id) {
		bookRepository.deleteById(id);
		return "redirect:/";
	}
}