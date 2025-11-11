package com.example.movie_recommend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie")   //테이블 이름이랑 클래스 이름 다를때 적는.
@Getter
@Setter
public class Movie {
	@Id
	@Column(name = "movie_id")
	private Long movieId;
	
	private String title;
	private String genre;
	
	@Column(name = "rating_age")
	private Integer ratingAge;
}