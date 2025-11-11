package com.example.company;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpDTO {
	private int empno;
	private String ename;
	private String job;
	private String hiredate;
	private int sal;
	private int deptno;
	private String dname;
}
