package com.example.jpashop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {
	private long cartId;
	private String userid;
	private String name;
	private long productCode;
	private String productName;
	private int price;
	private int amount;
	private int money;
}
