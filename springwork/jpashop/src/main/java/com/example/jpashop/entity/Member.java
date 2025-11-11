package com.example.jpashop.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
	@Id
	private String userid;
	
	private String passwd;
	private String name;
	
	@ColumnDefault("1")
	private int level;
	
	private String zipcode;
	private String address1;
	private String address2;
	private String tel;
	
	@OneToMany(mappedBy = "member")
	List<Cart> cartList = new ArrayList<>();
	
	@ToString.Exclude
	@OneToMany(mappedBy = "member")
	List<OrderItem> orderItemList = new ArrayList<>();
	
	@ToString.Exclude
	@OneToMany(mappedBy = "member")
	List<Board> boardList = new ArrayList<>();
	
	@ToString.Exclude
	@OneToMany(mappedBy = "member")
	List<Reply> replyList = new ArrayList<>();
	
	public Member(String userid) {
		this.userid = userid;
	}
	
	@PrePersist
	public void prePersist() {
		level = level == 0 ? 1 : level;
	}
}