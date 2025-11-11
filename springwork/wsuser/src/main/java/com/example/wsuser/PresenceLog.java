package com.example.wsuser;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PresenceLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String sessionId;
	private String nickname;
	
	@Enumerated(EnumType.STRING)  //0, 1 대신 문자열로 처리
	private EventType eventType;
	
	private OffsetDateTime atTs = OffsetDateTime.now();
	
	public enum EventType {
		JOIN, LEAVE
	}
}