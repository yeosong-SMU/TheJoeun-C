package com.example.expense;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDTO {
	private int idx;
	private String reg_date;
	private String category;
	private String description;
	private int amount;
}
