package com.example.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	//최근 메시지 조회 (최근 100개)
	List<ChatMessage> findTop100ByRoomOrderByIdDesc(String room);
}