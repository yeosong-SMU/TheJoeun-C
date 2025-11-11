package com.example.trigger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService service;
	private final MemberRepository repo;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("members", repo.findAllByOrderByIdDesc());
		return "index";
	}
	
	@PostMapping("/signup")
	public String signup(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		service.signup(username, password);
		return "redirect:/";
	}
	
	@PostMapping("/withdraw/{id}")
	public String withdraw(@PathVariable(name = "id") Long id) {
		service.withdraw(id);
		return "redirect:/";
	}
}