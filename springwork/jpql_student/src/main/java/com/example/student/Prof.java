package com.example.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prof {
	@Id
	private Integer profno;
	private String pname;
	private String position;
}