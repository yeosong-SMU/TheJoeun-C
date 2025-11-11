package com.example.movie;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByGenre(String genre);
	List<Movie> findByGenre(String genre, Sort sotr);
}