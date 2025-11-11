package com.example.todo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TodoDTO {
	private int idx;
	private String title;
	private String description;
	private int priority;
	private String done;
	private String reg_date;
	private String target_date;
}
