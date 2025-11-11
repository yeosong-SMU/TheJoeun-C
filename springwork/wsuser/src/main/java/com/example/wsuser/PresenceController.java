package com.example.wsuser;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresenceController {
	private final PresenceService presenceService;
	
	public PresenceController(PresenceService presenceService) {
		this.presenceService = presenceService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		var status = presenceService.currentStatus();
		model.addAttribute("initCount", status.getCount());
		return "index";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		var status = presenceService.currentStatus();
		model.addAttribute("initCount", status.getCount());
		model.addAttribute("initUsers", status.getUsers());
		return "admin";
	}
	
	@MessageMapping("/join")
	public void join(@Payload String nickname, 
			SimpMessageHeaderAccessor headers) {
		String sessionId = headers.getSessionId();
		presenceService.join(sessionId, nickname);
	}
}