package com.example.chat;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String room;
	private String nickname;
	
	@Column(length = 1000)
	private String content;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	public void onCreate() {
		if(createdAt == null)
			createdAt = LocalDateTime.now();
		if (room == null || room.isBlank())
			room = "myroom";
	}
	
	public ChatMessage(String room, String nickname, String content) {
		this.room = room;
		this.nickname = nickname;
		this.content = content;
	}
}