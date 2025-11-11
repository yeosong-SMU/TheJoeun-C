package com.example.jpashop.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderIdx;
	
	private Date orderDate;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private Member member;
	
	private int money;
	private int delivery;
	private int totalMoney;
	private String method;  //card, account
	private String cardNumber;
	private int zipcode;
	private String address1;
	private String address2;
	private String tel;
	private String status;
	private String cancelReason;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderDetail> detailList = new ArrayList<>();
	
	public OrderItem(int orderIdx) {
		this.orderIdx = orderIdx;
	}
}