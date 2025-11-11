package com.example.pivot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentPivotController {
	@Autowired
	StudentPivotService service;
	
	@GetMapping
	public String view(Model model) {
		model.addAttribute("rows", service.getDeptGradePivot());
		return "list";
	}
}