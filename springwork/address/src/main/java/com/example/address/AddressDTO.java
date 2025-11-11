package com.example.address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
	private int idx;
	private String name;
	private String address;
	private String phone;
}
