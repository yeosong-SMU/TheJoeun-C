package com.example.bookreview.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"reviews"})
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String author;
	private String publisher;
	
	@Temporal(TemporalType.DATE)  //날짜 타입. TemporalType.DATE, TIME, TIMESTAMP
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date publishedDate;
	
	@OneToMany(mappedBy = "book", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private List<Review> reviews;
}