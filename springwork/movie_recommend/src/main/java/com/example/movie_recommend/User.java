package com.example.movie_recommend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id
	@Column(name = "user_id")
	private Long userId;
	
	private String gender;
	private Integer age;
	private String region;
	
	@Column(name = "favorite_genre")
	private String favoriteGenre;
}