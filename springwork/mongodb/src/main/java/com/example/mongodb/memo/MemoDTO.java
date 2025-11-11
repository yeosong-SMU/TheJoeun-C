package com.example.mongodb.memo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemoDTO {
	private String _id;
	private String writer;
	private String memo;
	private Date post_date;
}