package com.example.org;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		model.addAttribute("title", "홈");
		model.addAttribute("message", "환영합니다.");
		return "home";
	}
}