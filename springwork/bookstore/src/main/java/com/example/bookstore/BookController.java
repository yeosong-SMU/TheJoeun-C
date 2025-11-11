package com.example.bookstore;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public String list(Model model) {
		List<Book> books = bookRepository.findAll();
		List<BookDTO> dtoList = books.stream().map(book -> {
			BookDTO dto = modelMapper.map(book, BookDTO.class);
			if(book.getCategory() != null) {
				dto.setCategoryId(book.getCategory().getId());
				dto.setCategoryName(book.getCategory().getName());
			}
			return dto;
		}).toList();
		model.addAttribute("bookList", dtoList);
		return "books/list";
	}
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("book", new BookDTO());
		model.addAttribute("categoryList", categoryRepository.findAll());
		return "books/add";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute BookDTO dto) {
		Book book = modelMapper.map(dto, Book.class);
		Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
		book.setCategory(category);
		bookRepository.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Book book = bookRepository.findById(id).orElseThrow();
		BookDTO dto = modelMapper.map(book, BookDTO.class);
		if(book.getCategory() != null) {
			dto.setCategoryId(book.getCategory().getId());
			dto.setCategoryName(book.getCategory().getName());
		}
		model.addAttribute("book", dto);
		model.addAttribute("categoryList", categoryRepository.findAll());
		return "books/edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute BookDTO dto) {
		Book book = modelMapper.map(dto, Book.class);
		Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
		book.setCategory(category);
		bookRepository.save(book);
		return "redirect:/books";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		bookRepository.deleteById(id);
		return "redirect:/books";
	}
}