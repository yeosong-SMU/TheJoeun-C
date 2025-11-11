package com.example.unpivot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScoreController {
	@Autowired
	ScoreService service;
	
	@GetMapping
	public String view(Model model,
			@RequestParam(name="studentId", required = false) Long studentId) {
		model.addAttribute("rows", service.listByStudent(studentId));
		model.addAttribute("studentId", studentId);
		return "list";
	}
}