package com.example.guestbook;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuestbookDTO {
	private int idx;
	private String name;
	private String email;
	private String content;
	private Date post_date;
}
