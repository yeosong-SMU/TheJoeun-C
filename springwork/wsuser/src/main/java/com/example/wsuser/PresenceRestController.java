package com.example.wsuser;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presence")
public class PresenceRestController {
	private final PresenceService presenceService;
	
	public PresenceRestController(PresenceService service) {
		this.presenceService = service;
	}
	
	@GetMapping("/snapshot")
	public Map<String, Object> snapshot(@RequestParam(name="limit", defaultValue = "10") int limit) {
		OnlineStatus status = presenceService.currentStatus();
		var logs = presenceService.recentLogs(limit);
		var rows = presenceService.aggregatedRows(limit);
		return Map.of("status", Map.of("count", status.getCount(), 
				"users", status.getUsers()), "logs",
				logs.stream().map(l -> Map.of("id", l.getId(), 
						"sessionId", l.getSessionId(), 
						"nickname", l.getNickname(), 
						"type", l.getEventType().name(),
						"at", l.getAtTs().toString())).toList(),
				"rows", rows);
	}
}