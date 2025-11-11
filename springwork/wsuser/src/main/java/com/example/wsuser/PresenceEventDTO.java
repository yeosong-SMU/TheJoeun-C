package com.example.wsuser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresenceEventDTO {
	private String type;
	private String nickname;
	private String at;
}