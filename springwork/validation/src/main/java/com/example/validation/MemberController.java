package com.example.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class MemberController {
	private final MemberRepository repo;
	private final InsecureMemberRepository insecure;
	
	public MemberController(MemberRepository repo, 
			InsecureMemberRepository insecure) {
		this.repo = repo;
		this.insecure = insecure;
	}
	
	@GetMapping
	public String home() {
		return "home";
	}
	
	@GetMapping("/join")
	public String createForm(Model model) {
		model.addAttribute("member", new Member());
		return "join";
	}
	
	@PostMapping("/join")
	public String create(@Valid @ModelAttribute Member member,
			BindingResult result) {
		if (result.hasErrors()) {
			return "join";
		}
		repo.save(member);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Member> members = repo.findAll();
		model.addAttribute("members", members);
		return "list";
	}
	
	@GetMapping("/search_form")
	public String searchForm(Model model) {
		model.addAttribute("email", "");
		return "search_form";
	}
	
	@PostMapping("/search_insecure")
	public String searchInsecure(@RequestParam("email") String email,
			Model model) {
		List<Member> members = insecure.findByEmailInsecure(email);
		System.out.println(members);
		model.addAttribute("results", members);
		model.addAttribute("mode", "insecure");
		return "search";
	}
	
	@PostMapping("/search")
	public String searchSafe(@RequestParam("email") String email, 
			Model model) {
		Member member = repo.findByEmail(email);
		model.addAttribute("results", member != null ? List.of(member) : List.of());
		model.addAttribute("mode", "safe");
		return "search";
	}
}