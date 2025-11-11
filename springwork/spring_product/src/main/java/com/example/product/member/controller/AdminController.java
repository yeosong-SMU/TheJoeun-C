package com.example.product.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.product.member.model.AdminDAO;
import com.example.product.member.model.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@Autowired
	AdminDAO adminDao;
	
	@GetMapping("login.do")
	public String login() {
		return "admin/login";
	}
	
	@PostMapping("login_check.do")
	public ModelAndView login_check(@ModelAttribute MemberDTO dto, HttpSession session, ModelAndView mav) {
		String name = adminDao.login(dto);
		if(name != null) {
			session.setAttribute("admin_userid", dto.getUserid());
			session.setAttribute("admin_name", name);
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("name", name);
			mav.setViewName("admin/admin");
			mav.addObject("message", "success");
		} else {
			mav.setViewName("admin/login");
			mav.addObject("message", "error");
		}
		return mav;
	}
	
	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/login.do?message=logout";
	}
}
