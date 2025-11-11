package com.example.movie;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieApiController {
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<MovieDTO> getAllMovies(@RequestParam(name="genre", required=false) String genre, 
			@RequestParam(name="sortBy", defaultValue = "title") String sortBy, 
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		List<Movie> movies;
		
		Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		if(genre != null) {
			movies = movieRepository.findByGenre(genre, Sort.by(sortDirection, sortBy));
		} else {
			movies = movieRepository.findAll(Sort.by(sortDirection, sortBy));
		}
		
		return movies.stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable("id") Long id) {
		return movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + id));
	}
	
	@PostMapping
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepository.save(movie);
	}
	
	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable("id") Long id, 
			@RequestBody Movie movie) {
		Movie existingMovie = movieRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + id));
		existingMovie.setTitle(movie.getTitle());
		existingMovie.setDirector(movie.getDirector());
		existingMovie.setReleaseYear(movie.getReleaseYear());
		existingMovie.setGenre(movie.getGenre());
		existingMovie.setRating(movie.getRating());
		
		return movieRepository.save(existingMovie);
	}
	
	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable("id") Long id) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + id));
		movieRepository.delete(movie);
	}
	
	@PostMapping("/{movieId}/reviews")
	public Review addReview(@PathVariable("movieId") Long movieId, 
			@RequestBody Review review) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + movieId));
		review.setMovie(movie);
		return reviewRepository.save(review);
	}
	
	@GetMapping("/{movieId}/reviews")
	public List<Review> getReviewsByMovie(@PathVariable("movieId") Long movieId) {
		return reviewRepository.findByMovieId(movieId);
	}
	
	@PutMapping("/{movieId}/reviews/{reviewId}")
	public Review updateReview(@PathVariable("movieId") Long movieId, 
			@PathVariable("reviewId") Long reviewId, 
			@RequestBody Review updatedReview) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + movieId));
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid review Id: " + reviewId));
		
		review.setContent(updatedReview.getContent());
		review.setRating(updatedReview.getRating());
		review.setReviewer(updatedReview.getReviewer());
		review.setMovie(movie);
		return reviewRepository.save(review);
	}
	
	@DeleteMapping("/{movieId}/reviews/{reviewId}")
	public void deleteReview(@PathVariable("movieId") Long movieId, 
			@PathVariable("reviewId") Long reviewId) {
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + movieId));
		
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid review Id: " + reviewId));
		
		reviewRepository.delete(review);
	}
}