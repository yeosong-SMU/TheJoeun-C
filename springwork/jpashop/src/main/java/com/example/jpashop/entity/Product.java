package com.example.jpashop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productCode;
	
	private String productName;
	
	private int price;
	
	@Column(columnDefinition = "text")
	private String description;
	
	private String filename;
	
	//cascade 연쇄작업, 상품이 삭제되면 장바구니도 삭제됨
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	List<Cart> cartList = new ArrayList<>();
	
	public Product(long productCode) {
		this.productCode = productCode;
	}
}