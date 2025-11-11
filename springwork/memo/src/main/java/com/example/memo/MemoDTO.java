package com.example.memo;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemoDTO {
	private int idx;
	private String writer;
	private String memo;
	private Date post_date;
	
	public MemoDTO(String writer, String memo) {
		this.writer = writer;
		this.memo = memo;
	}
}
