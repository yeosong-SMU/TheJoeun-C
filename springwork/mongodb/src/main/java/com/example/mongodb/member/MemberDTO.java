package com.example.mongodb.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private String _id;
	private String passwd;
	private String name;
}