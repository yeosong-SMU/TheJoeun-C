package com.example.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleContaining(String title);
	List<Book> findByAuthorContaining(String author);
	List<Book> findByGenreContaining(String genre);
}