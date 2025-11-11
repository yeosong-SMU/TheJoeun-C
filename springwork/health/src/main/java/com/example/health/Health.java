package com.example.health;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Health {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int age;
	private int gender;
	
	private double height;
	private double weight;
	private double bmi;
	
	private int systolicBp;
	private int diastolicBp;
	private int cholesterol;
	private int smoker;
	private int exerciseFreq;
	
	private Double score;
	private String message;
}