package com.example.jpashop.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDTO {
	private int idx;
	private int boardIdx;
	private String replyText;
	private Date regdate;
}