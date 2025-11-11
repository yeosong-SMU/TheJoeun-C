package com.example.unpivot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUnpivotRow {
	private Long studentId;
	private String subject;
	private Integer score;
}