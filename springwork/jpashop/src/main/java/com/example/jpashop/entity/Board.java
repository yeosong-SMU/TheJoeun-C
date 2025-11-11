package com.example.jpashop.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;
	
	private String title;
	private String contents;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private Member member;
	
	private Date regdate;
	
	@ColumnDefault("0")
	private int hit;
	
	@OneToMany(mappedBy = "board") 
	List<Attach> attachList = new ArrayList<>();
	
	@OneToMany(mappedBy = "board") 
	List<Reply> replyList = new ArrayList<>();
	
	public Board(int idx) {
		this.idx = idx;
	}
}