package com.example.movie_recommend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		List<Movie> movies = movieRepository.findAll();
		model.addAttribute("movies", movies);
		return "list";
	}
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String uploadCsv(@RequestParam(name="file") MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean isFirst = true;
			while ((line = reader.readLine()) != null) {
				if(isFirst) {
					isFirst = false;
					continue;
				} //skip header
				
				String[] parts = line.split(",");
				if(parts.length >= 4) {
					Movie movie = new Movie();
					movie.setMovieId(Long.parseLong(parts[0]));
					movie.setTitle(parts[1]);
					movie.setGenre(parts[2]);
					movie.setRatingAge(Integer.parseInt(parts[3]));
					movieRepository.save(movie);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name="id") Long id, 
			Model model) {
		Movie movie = movieRepository.findById(id).orElse(null);
		model.addAttribute("movie", movie);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String updateMovie(@RequestParam(name="movieId") Long movieId, 
			@RequestParam(name="title") String title, 
			@RequestParam(name="genre") String genre, 
			@RequestParam(name="ratingAge") Integer ratingAge) {
		Movie movie = movieRepository.findById(movieId).orElse(null);
		if(movie != null) {
			movie.setTitle(title);
			movie.setGenre(genre);
			movie.setRatingAge(ratingAge);
			movieRepository.save(movie);
		}
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String deleteMovie(@RequestParam(name="movieId") Long movieId) {
		movieRepository.deleteById(movieId);
		return "redirect:/";
	}
}