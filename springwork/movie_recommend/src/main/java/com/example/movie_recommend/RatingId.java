package com.example.movie_recommend;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RatingId implements Serializable {
	private Long userId;
	private Long movieId;
}