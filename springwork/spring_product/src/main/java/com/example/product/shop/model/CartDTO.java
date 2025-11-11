package com.example.product.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {
	private int cart_id;
	private String userid;
	private String name;
	private int product_code;
	private String product_name;
	private int price;
	private int amount;
	private int money;
}
