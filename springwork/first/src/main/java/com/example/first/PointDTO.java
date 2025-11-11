package com.example.first;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointDTO {
	private String name;
	private int kor;
	private int eng;
	private int mat;
	private int total;
	private double average;
}
