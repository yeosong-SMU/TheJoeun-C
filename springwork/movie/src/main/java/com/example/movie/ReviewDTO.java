package com.example.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
	private Long id;
    private String reviewer;
    private String content;
    private Double rating;
}