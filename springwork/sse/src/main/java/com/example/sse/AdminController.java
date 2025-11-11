package com.example.sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	private final NotificationService service;
	
	@GetMapping("/admin")
	public String adminPage() {
		return "admin";
	}
	
	@GetMapping("/admin/notifications/stream")
	public SseEmitter stream() {
		return service.subscribe();
	}
}