package com.example.bookreview.controller;

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

import com.example.bookreview.entity.Book;
import com.example.bookreview.entity.Review;
import com.example.bookreview.repository.BookRepository;
import com.example.bookreview.repository.ReviewRepository;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@GetMapping
	public String list(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "book/list";
	}
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("book", new Book());
		return "book/add";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute Book book) {
		bookRepository.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		Book book = bookRepository.findById(id).orElse(null);
		if(book == null) {
			return "redirect:/books";
		}
		List<Review> reviews = reviewRepository.findByBookId(id);
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
		return "book/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute Book book) {
		bookRepository.save(book);
		return "redirect:/books";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") Long id) {
		bookRepository.deleteById(id);
		return "redirect:/books";
	}
}