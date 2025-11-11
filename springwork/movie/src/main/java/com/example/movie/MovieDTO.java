package com.example.movie;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {
	private Long id;
    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private int rating;
    private String regdate;
    private List<ReviewDTO> reviews;
}