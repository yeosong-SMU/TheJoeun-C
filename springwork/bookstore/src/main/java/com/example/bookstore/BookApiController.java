package com.example.bookstore;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
@RequestMapping("/api")
public class BookApiController {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/books")
	public List<BookDTO> listBooks() {
		return bookRepository.findAll().stream().map(book -> {
			BookDTO dto = modelMapper.map(book, BookDTO.class);
			dto.setCategoryId(book.getCategory().getId());
			dto.setCategoryName(book.getCategory().getName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/books/{id}")
	public BookDTO getBook(@PathVariable("id") Long id) {
		Book book = bookRepository.findById(id).orElseThrow();
		BookDTO dto = modelMapper.map(book, BookDTO.class);
		dto.setCategoryId(book.getCategory().getId());
		dto.setCategoryName(book.getCategory().getName());
		return dto;
	}
	
	@PostMapping("/books")
	public void addBook(@RequestBody BookDTO dto) {
		Book book = modelMapper.map(dto, Book.class);
		Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
		book.setCategory(category);
		bookRepository.save(book);
	}
	
	@PutMapping("/books/{id}")
	public void updateBook(@PathVariable("id") Long id, 
			@RequestBody BookDTO dto) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
		book.setCategory(category);
		bookRepository.save(book);
	}
	
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable("id") Long id) {
		bookRepository.deleteById(id);
	}
	
	@GetMapping("/categories")
	public List<CategoryDTO> listCategories() {
		return categoryRepository.findAll().stream().map(c -> modelMapper.map(c, CategoryDTO.class))
				.collect(Collectors.toList());
	}
}