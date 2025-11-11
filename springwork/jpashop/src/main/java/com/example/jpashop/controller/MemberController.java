package com.example.jpashop.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.jpashop.dto.MemberDTO;
import com.example.jpashop.entity.Member;
import com.example.jpashop.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("insert_member")
	public String insert_member(MemberDTO dto) {
		Member m = modelMapper.map(dto, Member.class);
		memberRepository.save(m);
		return "redirect:/member/login";
	}
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("login_check")
	public ModelAndView login_check(MemberDTO dto, HttpSession session) {
		Optional<Member> result = memberRepository.findByUseridAndPasswd(dto.getUserid(), dto.getPasswd());
		ModelAndView mav = new ModelAndView();
		if(result.isPresent()) {
			Member m = result.get();
			String name = m.getName();
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("name", name);
			session.setAttribute("level", m.getLevel());
			String redirectURI = (String)session.getAttribute("redirectURI");
			if(redirectURI != null) {
				mav.setViewName("redirect:" + redirectURI);
				return mav;
			}
			else {
				mav.setViewName("member/main");
			}
		} else {
			mav.setViewName("member/login");
			mav.addObject("message", "error");
		}
		return mav;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		session.invalidate();
		mav.setViewName("member/login");
		mav.addObject("message", "logout");
		return mav;
	}
}