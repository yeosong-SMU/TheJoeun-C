package com.example.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleContaining(String title);
	
	List<Book> findByAuthorContaining(String author);
	
	List<Book> findByGenreContaining(String genre);
}