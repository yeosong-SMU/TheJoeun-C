package com.example.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/members")
public class MemberController {
	@Autowired
	private MemberRepository repo;
	
	@GetMapping
	public List<Member> getAll() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Member getOne(@PathVariable(name="id") String id) {
		return repo.findById(id).orElse(null);
	}
	
	@PostMapping
	public void insert(@RequestBody Member member) {
		repo.register(member.getId(), member.getPassword(), member.getName(), member.getEmail());
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable(name = "id") String id, 
			@RequestBody Member req) {
		Member member = repo.findById(id).orElse(null);
		if(member != null) {
			member.setName(req.getName());
			member.setEmail(req.getEmail());
			member.setRole(req.getRole());
			repo.save(member);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name="id") String id) {
		repo.deleteById(id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Member> login(@RequestBody Member req, 
			HttpSession session) {
		Member member = repo.findByLogin(req.getId(), req.getPassword());
		if(member != null) {
			session.setAttribute("loginId", member.getId());
			session.setAttribute("loginName", member.getName());
			session.setAttribute("role", member.getRole());
			return ResponseEntity.ok(member);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  //401 Unauthorized
		}
	}
	
	@GetMapping("/session")
	public Map<String, Object> getSessionInfo(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("loginId", session.getAttribute("loginId"));
		map.put("loginName", session.getAttribute("loginName"));
		map.put("role", session.getAttribute("role"));
		return map;
	}
	
	@PostMapping("/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
}