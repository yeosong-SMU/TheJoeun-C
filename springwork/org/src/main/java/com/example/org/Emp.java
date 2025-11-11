package com.example.org;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emp {
	@Id
	private Long empno;
	private String ename;
	private String job;
	private Long mgr;
}