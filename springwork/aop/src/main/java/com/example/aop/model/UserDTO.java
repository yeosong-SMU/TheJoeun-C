package com.example.aop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	private String userid;
	private String pwd;
	private String name;
	private int point;
}
