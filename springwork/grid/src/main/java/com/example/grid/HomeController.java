package com.example.grid;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final StudentScoreService service;
	
	@GetMapping({"", "/"})
	public String listStudents(Model model) {
		List<StudentScore> scores = service.findAllScores();
		model.addAttribute("scores", scores);
		return "list";
	}
	
	@GetMapping("/add")
	public String addForm() {
		return "add";
	}
}