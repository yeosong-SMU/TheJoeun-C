package com.example.bookstore;

import lombok.Data;

@Data
public class BookDTO {
	private Long id;
	private String title;
	private String author;
	private Long categoryId;
	private String categoryName;
}