package com.example.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	@NotBlank
	private String name;
	
	@PositiveOrZero
	private BigDecimal price;
	
	@Size(max = 2000)
	private String description;
	
	@Size(max = 500)
	private String imageUrl;
}