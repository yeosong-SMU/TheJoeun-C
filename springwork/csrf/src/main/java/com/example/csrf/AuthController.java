package com.example.csrf;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
	private final MemberRepository repo;
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		model.addAttribute("loginUser", session.getAttribute("LOGIN_USER"));
		return "index";
	}
	
	@GetMapping("/signup")
	public String signupForm(Model model, 
			HttpSession session) {
		ensureCsrf(session, model);
		model.addAttribute("member", new Member());
		return "signup";
	}
	
	@PostMapping("/signup")
	@Transactional
	public String signup(@RequestParam("userid") String userid, 
			@RequestParam("pwd") String pwd,
			@RequestParam("name") String name) {
		repo.insertWithHash(userid, pwd, name);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginForm(Model model, HttpSession session) {
		ensureCsrf(session, model);
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("userid") String userid,
			@RequestParam("pwd") String pwd,
			HttpSession session,
			Model model) {
		var userOpt = repo.loginRaw(userid, pwd);
		if (userOpt.isEmpty()) {
			model.addAttribute("error", "아이디/비밀번호를 확인하세요.");
			ensureCsrf(session, model);
			return "login";
		}
		session.setAttribute("LOGIN_USER", userOpt.get().getUserid());
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	private void ensureCsrf(HttpSession session, Model model) {
		String token = (String) session.getAttribute("CSRF_TOKEN");
		if (token == null) {
			token = Csrf.newToken();
			session.setAttribute("CSRF_TOKEN", token);
		}
		model.addAttribute("_csrf", token);
	}
}