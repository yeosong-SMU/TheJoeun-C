package com.example.aop.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDTO {
	private int idx;
	private String receive_id;
	private int receive_point;
	private String sender;
	private int sender_point;
	private String message;
	private Date open_date;
	private Date send_date;
}
