package com.example.aop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.aop.model.MessageDTO;
import com.example.aop.model.UserDTO;
import com.example.aop.service.MessageService;

@Controller
public class MessageController {
	@Autowired
	MessageService service;
	
	@GetMapping("/")
	public ModelAndView list() {
		List<UserDTO> user = service.list_user();
		List<MessageDTO> message = service.list_message();
		
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("message", message);
		
		return new ModelAndView("list", "map", map);
	}
	
	@PostMapping("insert.do")
	public String insert(MessageDTO dto) {
		service.insertMessage(dto);
		return "redirect:/";
	}
	
	@GetMapping("read.do")
	public String read(@RequestParam(name = "userid") String userid, @RequestParam(name = "idx") int idx) {
		service.read(userid, idx);
		return "redirect:/";
	}
}