package com.example.jwt;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final JwtUtil jwtUtil;
	private final MemberRepository repo;
	
	@GetMapping("/members")
	public String list(HttpServletRequest req, Model model) {
		var current = requireAdmin(req);
		if(current == null)
			return "redirect:/login";
		model.addAttribute("members", repo.findAll());
		return "admin/list";
	}
	
	@GetMapping("/members/{id}/edit")
	public String editForm(@PathVariable(name="id") Long id, 
			HttpServletRequest req, 
			Model model) {
		var current = requireAdmin(req);
		if(current == null)
			return "redirect:/login";
		
		Member m = repo.findById(id).orElseThrow();
		MemberDTO form = new MemberDTO();
		form.setDisplayName(m.getDisplayName());
		form.setRole(m.getRole());
		
		model.addAttribute("memberId", id);
		model.addAttribute("form", form);
		return "admin/edit";
	}
	
	@PostMapping("/members/{id}/edit")
	public String edit(@PathVariable(name="id") Long id, 
			@ModelAttribute("form") MemberDTO form, 
			HttpServletRequest req) {
		var current = requireAdmin(req);
		if(current == null)
			return "redirect:/login";
		
		Member m = repo.findById(id).orElseThrow();
		m.setDisplayName(form.getDisplayName());
		if(form.getRole() != null && !form.getRole().isBlank()) {
			m.setRole(form.getRole().toUpperCase());
		}
		repo.save(m);
		return "redirect:/admin/members";
	}
	
	@PostMapping("/members/{id}/delete")
	public String delete(@PathVariable(name="id") Long id, 
			HttpServletRequest req) {
		var current = requireAdmin(req);
		if(current == null)
			return "redirect:/login";
		
		repo.deleteById(id);
		return "redirect:/admin/members";
	}
	
	private Member requireAdmin(HttpServletRequest req) {
		Optional<String> tokenOpt = getTokenFromCookie(req);  //쿠키에 포함된 토큰 조회
		if(tokenOpt.isEmpty())
			return null;
		
		try {
			//토큰 내용 - sub (Subject, 사용자 식별자), 
			//         iss (Issuer, 발급자),
			//         exp (Expiration, 만료 시간),
			//         iat (Issued At, 발급 시각)
			Claims claims = jwtUtil.parse(tokenOpt.get()).getBody();
			String username = claims.getSubject();
			return repo.findByUsername(username)
					.filter(m -> "ADMIN".equalsIgnoreCase(m.getRole()))
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Optional<String> getTokenFromCookie(HttpServletRequest req) {
		if(req.getCookies() == null)
			return Optional.empty();
		return Arrays.stream(req.getCookies())
				.filter(c -> "ACCESS_TOKEN".equals(c.getName()))
				.map(Cookie::getValue).findFirst();
	}
}