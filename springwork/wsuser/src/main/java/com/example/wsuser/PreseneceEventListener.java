package com.example.wsuser;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PreseneceEventListener {
	private final PresenceService presenceService;
	
	public PreseneceEventListener(PresenceService presenceService) {
		this.presenceService = presenceService;
	}
	
	// 접속해제 이벤트
	@EventListener
	public void handleSessionDisconnect(SessionDisconnectEvent event) {
		presenceService.leave(event.getSessionId());
	}
	
	// 접속 이벤트
	@EventListener
	public void handleSessionConnected(SessionConnectEvent event) {
	}
}