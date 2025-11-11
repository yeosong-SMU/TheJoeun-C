package com.example.jpashop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private String userid;
	private String passwd;
	private String name;
	private int level;
	private String zipcode;
	private String address1;
	private String address2;
	private String tel;
}