package com.example.qmember;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchDTO {
	private String keyword;
	private String role;
	private Boolean enabled;
	
	private LocalDateTime createdFrom;
	private LocalDateTime createdTo;
	
	private String sort = "createdAt";
	private String dir = "desc";
	
	private Integer page = 0;
	private Integer size = 10;
}