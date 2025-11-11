package com.example.insurance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int age;
	private int gender;
	private int drivingExperience;
	private int accidentHistory;
	private int vehicleType;
	private int annualMileage;
	private int vehicleAge;
	private int location;
	private int creditScore;
	private int vehicleValue;
	private int insurance;
}