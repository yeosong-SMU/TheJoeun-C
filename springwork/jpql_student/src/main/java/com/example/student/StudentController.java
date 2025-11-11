package com.example.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
	@Autowired
	private StudentRepository studRepository;
	
	@GetMapping("/")
	public String viewAllStudents(Model model) {
		List<Object[]> students = studRepository.findAllWithMajorAndProf();
		model.addAttribute("students", students);
		return "list";
	}
	
	@GetMapping("/grade")
	public String viewStudentsByGrade(Model model, 
			@RequestParam(name = "grade", defaultValue = "0") int grade) {
		List<Object[]> students = studRepository.findByGrade(grade);
		model.addAttribute("students", students);
		model.addAttribute("selectedGrade", grade);
		return "list";
	}
	
	@GetMapping("/prof")
	public String viewStudentsByProf(Model model, 
			@RequestParam(name = "profName", required = false) String profName) {
		if(profName == null || profName.isEmpty()) {
			profName = null;
		}
		List<Object[]> students = studRepository.findByProfName(profName);
		model.addAttribute("students", students);
		return "list";
	}
}