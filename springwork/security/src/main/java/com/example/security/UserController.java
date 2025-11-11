package com.example.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	PasswordEncoder pwdEncoder;
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/user/login.do")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/user/join.do")
	public String join() {
		return "user/join";
	}
	
	@RequestMapping("/admin/*")
	public String admin_main() {
		return "admin/main";
	}
	
	@PostMapping("/user/insert.do")
	public String insert(@RequestParam(name = "userid") String userid, 
			@RequestParam(name = "passwd") String passwd, 
			@RequestParam(name = "name") String name, 
			@RequestParam(name = "authority") String authority) {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		String pwd = pwdEncoder.encode(passwd);
		map.put("passwd", pwd);
		map.put("name", name);
		map.put("authority", authority);
		userDao.insert(map);
		return "user/login";
	}
	
	@GetMapping("/user/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}