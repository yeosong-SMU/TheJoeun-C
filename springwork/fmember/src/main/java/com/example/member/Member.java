package com.example.member;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
	@Id
	private String id;
	
	private String password;
	private String name;
	private String email;
	private String role;
	private LocalDate regdate;
	
	@PrePersist
	public void onCreate() {
		this.regdate = LocalDate.now();
		if(this.role == null)
			this.role = "user";
	}
}