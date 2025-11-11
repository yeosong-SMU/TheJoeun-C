package com.example.jpashop.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
	private int idx;
	private String title;
	private String contents;
	private String userid;
	private Date regdate;
	private int hit;
	private String[] files;
}