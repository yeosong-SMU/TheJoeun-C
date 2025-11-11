package com.example.bookreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookreview.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}