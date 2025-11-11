package com.example.jpashop.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDTO {
	private long productCode;
	private String productName;
	private int price;
	private String description;
	private String filename;
	private MultipartFile img;
	
	public ProductDTO(long productCode, String productName, int price, String description, String filename) {
		this.productCode = productCode;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.filename = filename;
	}
}