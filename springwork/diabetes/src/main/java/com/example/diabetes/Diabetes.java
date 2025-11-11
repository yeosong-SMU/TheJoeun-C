package com.example.diabetes;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Diabetes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer pregnancies;
	private Integer glucose;
	private Integer bloodPressure;
	private Integer skinThickness;
	private Integer insulin;
	private Double bmi;
	private Double diabetesPedigree;
	private Integer age;
	private Integer prediction;
	private Double probability;
	
	private LocalDateTime createdAt;
}