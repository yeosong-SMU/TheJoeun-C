package com.example.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Major {
	@Id
	private Integer majorno;
	private String mname;
}