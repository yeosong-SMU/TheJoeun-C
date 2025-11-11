package com.example.apartment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Apartment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String address;
	
	private Double area;
	
	private Integer floor;
	
	private Integer rooms;
	
	private Integer builtYear;
	
	private Integer price;
	
	private Integer predictedPrice;
}
