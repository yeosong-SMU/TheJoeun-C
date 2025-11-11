package com.example.chat;

import java.util.Collections;
import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final ChatMessageRepository repo;
	private final SimpMessagingTemplate template;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	//최근 기록 조회
	@GetMapping("/api/history/{room}")
	@ResponseBody
	public List<ChatMessage> history(@PathVariable(name = "room") String room) {
		var list = repo.findTop100ByRoomOrderByIdDesc(room);
		Collections.reverse(list);
		return list;
	}
	
	//메시지 수신 => DB 저장 => 구독자에게 전송
	@MessageMapping("/send/{room}")
	public void receiveAndBroadcast(@Payload ChatMessage incoming, 
			@DestinationVariable("room") String room) {
		ChatMessage msg = new ChatMessage(room, incoming.getNickname(), incoming.getContent());
		ChatMessage saved = repo.save(msg);
		//구독자에게 브로드캐스트
		template.convertAndSend("/topic/room/" + room, saved);
	}
}