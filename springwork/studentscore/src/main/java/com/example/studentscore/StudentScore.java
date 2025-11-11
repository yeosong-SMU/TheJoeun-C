package com.example.studentscore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StudentScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int age;
	private int studytime;
	private int failures;
	private int absences;
	private int g1;
	private int g2;
	private double g3;
	private int medu;
	private int fedu;
}