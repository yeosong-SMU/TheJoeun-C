package com.example.myjpa;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {
	@Id
	private String userid;
	private String passwd;
	private String name;
	private String email;
	private Date join_date;
}