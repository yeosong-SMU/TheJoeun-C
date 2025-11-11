package com.example.mongodb.guestbook;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuestbookDTO {
	private String _id;
	private String name;
	private String email;
	private String contents;
	private Date post_date;
}