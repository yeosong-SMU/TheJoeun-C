package com.example.churn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Churn {
	@Id
	private Long customerId;
	
	private Integer age;
	private String gender;
	private Integer tenureMonths;
	private Integer monthlyCharges;
	private String contractType;
	private String paymentMethod;
	private String internetService;
	
	@Column(nullable = false)
	private Integer churn = 0;
	
	@Column(nullable = false)
	private Double churnProbability = 0.0;
}