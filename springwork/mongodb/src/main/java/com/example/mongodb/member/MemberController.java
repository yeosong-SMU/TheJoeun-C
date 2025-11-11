package com.example.mongodb.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("member/*")
public class MemberController {
	@Autowired
	MemberDAO memberDao;
	
	@GetMapping("join.do")
	public ModelAndView join() {
		return new ModelAndView("member/join");
	}
	
	@PostMapping("insert.do")
	public String insert(@RequestParam(name = "_id") String _id, 
			@RequestParam(name = "passwd") String passwd, 
			@RequestParam(name = "name") String name) {
		MemberDTO dto = new MemberDTO();
		dto.set_id(_id);
		dto.setPasswd(passwd);
		dto.setName(name);
		memberDao.join(dto);  //document 저장
		return "redirect:/member/login.do";
	}
	
	@GetMapping("login.do")
	public ModelAndView login() {
		return new ModelAndView("member/login");
	}
	
	@PostMapping("login_check.do")
	public ModelAndView login_check(@RequestParam(name = "_id") String _id, 
			@RequestParam(name = "passwd") String passwd, 
			HttpSession session) {
		MemberDTO dto = memberDao.login(_id, passwd);
		String message = "";
		String url = "";
		if(dto == null) {
			message = "로그인 정보가 정확하지 않습니다.";
			url = "member/login";
		} else {
			message = dto.getName() + "님 환영합니다.";
			url = "home";
			session.setAttribute("userid", _id);
			session.setAttribute("name", dto.getName());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		return new ModelAndView(url, "map", map);
	}
	
	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();   //세션 초기화
		return "redirect:/member/login.do";
	}
}