package com.example.movie_recommend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rating")
@IdClass(RatingId.class)
@Getter
@Setter
public class Rating {
	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@Id
	@Column(name = "movie_id")
	private long movieId;
	
	private Double rating;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="movie_id", insertable=false, updatable=false)
	private Movie movie;
}