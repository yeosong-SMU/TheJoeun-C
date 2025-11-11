package com.example.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/movies")
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@GetMapping
	public String getAllmovies(@RequestParam(name="genre", required=false) String genre, 
			Model model) {
		if(genre != null) {
			model.addAttribute("movies", movieRepository.findByGenre(genre));
		} else {
			model.addAttribute("movies", movieRepository.findAll());
		}
		return "list";
	}
	
	@GetMapping("/{id}")
	public String getMovieDetails(@PathVariable(name="id") Long id, 
			Model model) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
		model.addAttribute("movie", movie);
		model.addAttribute("reviews", reviewRepository.findByMovieId(id));
		model.addAttribute("review", new Review());
		return "detail";
	}
	
	@GetMapping("/edit/{id}")
	public String editMovie(@PathVariable(name="id") Long id, 
			Model model) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
		model.addAttribute("movie", movie);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String updateMovie(Movie movie) {
		Movie existingMovie = movieRepository.findById(movie.getId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + movie.getId()));
		existingMovie.setTitle(movie.getTitle());
		existingMovie.setDirector(movie.getDirector());
		existingMovie.setReleaseYear(movie.getReleaseYear());
		existingMovie.setGenre(movie.getGenre());
		existingMovie.setRating(movie.getRating());
		movieRepository.save(existingMovie);
		return "redirect:/movies";
	}
	
	@PostMapping("/delete")
	public String deleteMovie(@RequestParam(name="id") Long id) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
		movieRepository.delete(movie);
		return "redirect:/movies";
	}
	
	@GetMapping("/add")
	public String addMovieForm(@RequestParam(name="defaultGenre", required = false) String defaultGenre, 
			Model model) {
		Movie movie = new Movie();
		if(defaultGenre != null) {
			movie.setGenre(defaultGenre);
		}
		model.addAttribute("movie", movie);
		return "add";
	}
	
	@PostMapping("/add")
	public String addMovie(Movie movie) {
		movieRepository.save(movie);
		return "redirect:/movies";
	}
	
	@PostMapping("/review/{id}")
	public String addReview(@PathVariable(name="id") Long id, 
			@RequestParam(name="reviewer") String reviewer, 
			@RequestParam(name="content") String content, 
			@RequestParam(name="rating") int rating) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if(movie == null) {
			return "redirect:/movies";
		}
		
		Review review = new Review();
		review.setMovie(movie);
		review.setReviewer(reviewer);
		review.setContent(content);
		review.setRating(rating);
		reviewRepository.save(review);
		
		return "redirect:/moview/edit/" + id;
	}
	
	@PostMapping("/review/update")
	public String updateReview(@RequestParam(name="reviewId") Long reviewId, 
			@RequestParam(name="reviewer") String reviewer, 
			@RequestParam(name="content") String content, 
			@RequestParam(name = "rating") int rating) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		if(review == null) {
			return "redirect:/movies";
		}
		review.setReviewer(reviewer);
		review.setContent(content);
		review.setRating(rating);
		reviewRepository.save(review);
		
		return "redirect:/moview/edit/" + review.getMovie().getId();
	}
	
	@DeleteMapping("/review/delete/{reviewId}")
	@ResponseBody
	public void deleteReview(@PathVariable(name="reviewId") Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}
	
	@GetMapping("/review/{id}")
	@ResponseBody
	public Review getReview(@PathVariable(name = "id") Long id) {
		return reviewRepository.findById(id).orElse(null);
	}
}