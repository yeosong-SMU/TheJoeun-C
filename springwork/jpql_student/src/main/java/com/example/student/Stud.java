package com.example.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stud {
	@Id
	private Long studno;
	private String sname;
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "majorno")
	private Major major;
	
	@ManyToOne
	@JoinColumn(name = "profno")
	private Prof prof;
}