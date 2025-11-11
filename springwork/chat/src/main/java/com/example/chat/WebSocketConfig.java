package com.example.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  //웹소켓 연결 설정 지원 기능
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker (MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");  //웹소켓 구독 주소 prefix
		config.setApplicationDestinationPrefixes("/app"); //메시지 발신 주소 prefix
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//웹소켓 연결 주소 ws://host:port/ws
		//프로토콜://ip:포트
		//http
		//ftp
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
	}
}