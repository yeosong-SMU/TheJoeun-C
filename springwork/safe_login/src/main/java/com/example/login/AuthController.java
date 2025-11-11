package com.example.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Autowired
	UserRepository repo;
	
	@Autowired
	LoginService service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, 
			@RequestParam("password") String password,
			HttpServletRequest req,
			Model model) {
		String ip = req.getRemoteAddr();
		String acctKey = "acct:" + id;
		String ipKey = "ip:" + ip;
		
		//계정 차단 체크
		if (service.isAccountBlocked(acctKey)) {
			model.addAttribute("error", "계정이 잠겼습니다. 잠시 후 다시 시도하세요.");
			model.addAttribute("id", id);
			return "login";
		}
		
		//IP 차단 체크
		if (service.isIpBlocked(ipKey)) {
			model.addAttribute("error", "해당 IP에서 시도 횟수가 너무 많습니다. 잠시 후 다시 시도하세요.");
			model.addAttribute("id", id);
			return "login";
		}
		
		Optional<User> ou = repo.findByIdAndPassword(id, password);
		if(ou.isPresent()) {
			//로그인 성공 시 계정/아이피 카운트 초기화
			service.loginSucceededAccount(acctKey);
			service.loginSucceededIp(ipKey);
			
			HttpSession session = req.getSession();
			session.setAttribute("user", id);
			
			return "redirect:/welcome";
		} else {
			//로그인 실패 시 계정/아이피 카운트 증가
			service.loginFailedAccount(acctKey);
			service.loginFailedIp(ipKey);
			
			//계정 로그인 시도 횟수
			int attempts = service.getAccountAttempts(acctKey);
			model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다. 시도 횟수: " + attempts);
			model.addAttribute("id", id);
			return "login";
		}
	}
	
	@GetMapping("/welcome")
	public String welcome(HttpServletRequest req,
			Model model,
			HttpSession session) {
		if (session == null || session.getAttribute("user") == null) {
			model.addAttribute("error", "로그인되지 않았습니다.");
			return "login";
		}
		Object u = session.getAttribute("user");
		model.addAttribute("message", "환영합니다, " + u.toString() + "님!");
		return "welcome";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, 
			HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}