package com.example.pdf;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emp {
	@Id
	private Long empno;
	
	private String ename;
	private String job;
	private Long mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptno")
	private Dept dept;
}