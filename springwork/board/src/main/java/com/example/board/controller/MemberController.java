package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.model.MemberDAO;
import com.example.board.model.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	@Autowired
	MemberDAO memberDao;
	
	@GetMapping("address.do")
	public String address() {
		return "member/join";
	}
	
	@GetMapping("login.do")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("login_check.do")
	public ModelAndView login_check(MemberDTO dto, HttpSession session) {
		String name = memberDao.login(dto);
		if(name != null) {
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("name", name);
		}
		ModelAndView mav = new ModelAndView();
		if(name != null) {
			mav.setViewName("main");
		} else {
			mav.setViewName("member/login");
			mav.addObject("message", "error");
		}
		return mav;
	}
	
	@GetMapping("logout.do")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		session.invalidate();
		mav.setViewName("member/login");
		mav.addObject("message", "logout");
		return mav;
	}
}
