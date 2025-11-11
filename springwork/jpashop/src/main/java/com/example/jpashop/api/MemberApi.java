package com.example.jpashop.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.jpashop.dto.MemberDTO;
import com.example.jpashop.entity.Member;
import com.example.jpashop.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member/*")
public class MemberApi {
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping("join")
	public Map<String, Object> join(@ModelAttribute MemberDTO dto) {
		Member m = modelMapper.map(dto, Member.class);
		m.setLevel(1);
		Member m2 = memberRepository.save(m);
		Map<String, Object> map = new HashMap<>();
		if(m2 != null) {
			map.put("message", "success");
		} else {
			map.put("message", "error");
		}
		return map;
	}
	
	@PostMapping("login")
	public Map<String, Object> login(MemberDTO dto) {
		Optional<Member> result = memberRepository.findByUseridAndPasswd(dto.getUserid(), dto.getPasswd());
		Map<String, Object> map = new HashMap<>();
		if(result.isPresent()) {
			Member m = result.get();
			map.put("message", "success");
			map.put("name", m.getName());
			map.put("level", m.getLevel());
		} else {
			map.put("message", "error");
		}
		return map;
	}
}