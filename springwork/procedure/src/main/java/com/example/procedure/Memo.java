package com.example.procedure;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Memo {
	private Long id;
	private String content;
	private LocalDateTime createdAt;
}